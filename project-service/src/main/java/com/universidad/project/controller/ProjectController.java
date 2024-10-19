package com.universidad.project.controller;

import com.universidad.project.model.Project;
import com.universidad.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public Mono<Project> createProject(@Valid @RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'EVALUADOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Project> getProjectById(@PathVariable String projectId) {
        return projectService.findByProjectId(projectId);
    }

    @GetMapping("/student/{estudianteId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Project> getProjectsByStudentId(@PathVariable String estudianteId) {
        return projectService.findByEstudianteId(estudianteId);
    }

    @GetMapping("/advisor/{asesorId}")
    @PreAuthorize("hasAnyRole('ASESOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Project> getProjectsByAdvisorId(@PathVariable String asesorId) {
        return projectService.findByAsesorId(asesorId);
    }

    @GetMapping("/research-line/{researchLineId}")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Project> getProjectsByResearchLineId(@PathVariable String researchLineId) {
        return projectService.findByResearchLineId(researchLineId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Project> getAllProjects() {
        return projectService.findAll();
    }

    @PutMapping("/{projectId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Project> updateProject(@PathVariable String projectId, @Valid @RequestBody Project project) {
        return projectService.updateProject(projectId, project);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Void> deleteProject(@PathVariable String projectId) {
        return projectService.deleteProject(projectId);
    }

    @PostMapping("/{projectId}/upload")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public Mono<ResponseEntity<String>> uploadFile(@PathVariable String projectId,
                                                   @RequestPart("file") Mono<FilePart> filePartMono) {
        return filePartMono
                .flatMap(filePart -> projectService.uploadFile(projectId, filePart))
                .map(filename -> ResponseEntity.ok("Archivo " + filename + " subido exitosamente."));
    }

    @GetMapping("/{projectId}/files/{filename}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'EVALUADOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Void> downloadFile(@PathVariable String projectId,
                                   @PathVariable String filename,
                                   ServerHttpResponse response) {
        return projectService.downloadFile(projectId, filename, response);
    }
}
