package com.universidad.project.service;

import com.universidad.project.model.Project;
import com.universidad.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.universidad.project.model.ProjectStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Mono<Project> createProject(Project project) {
        project.setEstado(ProjectStatus.EN_DESARROLLO);
        project.setFechaCreacion(LocalDateTime.now());
        project.setFechaActualizacion(LocalDateTime.now());
        return projectRepository.save(project);
    }

    @Override
    public Mono<Project> updateProject(String projectId, Project project) {
        return projectRepository.findById(projectId)
                .flatMap(existingProject -> {
                    existingProject.setTitulo(project.getTitulo());
                    existingProject.setDescripcion(project.getDescripcion());
                    existingProject.setAsesorId(project.getAsesorId());
                    existingProject.setResearchLineId(project.getResearchLineId());
                    existingProject.setEstado(project.getEstado());
                    existingProject.setComentarios(project.getComentarios());
                    existingProject.setFechaActualizacion(LocalDateTime.now());

                    // Incrementar la versión
                    existingProject.setVersion(existingProject.getVersion() + 1);

                    return projectRepository.save(existingProject);
                });
    }

    @Override
    public Mono<Project> findByProjectId(String projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public Flux<Project> findByEstudianteId(String estudianteId) {
        return projectRepository.findByEstudiantesContains(estudianteId);
    }

    @Override
    public Flux<Project> findByAsesorId(String asesorId) {
        return projectRepository.findByAsesorId(asesorId);
    }

    @Override
    public Flux<Project> findByResearchLineId(String researchLineId) {
        return projectRepository.findByResearchLineId(researchLineId);
    }

    @Override
    public Flux<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Mono<Void> deleteProject(String projectId) {
        return projectRepository.deleteById(projectId);
    }

    @Override
    public Mono<String> uploadFile(String projectId, FilePart filePart) {
        return projectRepository.findById(projectId)
                .flatMap(project -> {
                    String filename = filePart.filename();
                    String storagePath = "uploads/projects/" + projectId + "/" + filename;
                    Path path = Paths.get(storagePath);

                    // Crear directorio si no existe
                    try {
                        Files.createDirectories(path.getParent());
                    } catch (IOException e) {
                        return Mono.error(e);
                    }

                    return filePart.transferTo(path)
                            .then(Mono.defer(() -> {
                                if (project.getArchivos() == null) {
                                    project.setArchivos(new ArrayList<>());
                                }
                                project.getArchivos().add(filename);
                                project.setFechaActualizacion(LocalDateTime.now());
                                return projectRepository.save(project)
                                        .thenReturn(filename);
                            }));
                });
    }

    @Override
    public Mono<Void> downloadFile(String projectId, String filename, ServerHttpResponse response) {
        return projectRepository.findById(projectId)
                .flatMap(project -> {
                    if (project.getArchivos() != null && project.getArchivos().contains(filename)) {
                        String storagePath = "uploads/projects/" + projectId + "/" + filename;
                        Path path = Paths.get(storagePath);

                        if (!Files.exists(path)) {
                            return Mono.error(new FileNotFoundException("El archivo no existe."));
                        }

                        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
                        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);

                        Flux<DataBuffer> dataBufferFlux = DataBufferUtils.read(path, new DefaultDataBufferFactory(), 4096);
                        return response.writeWith(dataBufferFlux);
                    } else {
                        return Mono.error(new FileNotFoundException("No tienes permiso para acceder a este archivo."));
                    }
                });
    }
}
