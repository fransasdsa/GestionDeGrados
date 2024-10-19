package com.universidad.project.service;

import com.universidad.project.model.Project;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
    Mono<Project> createProject(Project project);
    Mono<Project> updateProject(String projectId, Project project);
    Mono<Project> findByProjectId(String projectId);
    Flux<Project> findByEstudianteId(String estudianteId);
    Flux<Project> findByAsesorId(String asesorId);
    Flux<Project> findByResearchLineId(String researchLineId);
    Flux<Project> findAll();
    Mono<String> uploadFile(String projectId, FilePart filePart);
    Mono<Void> downloadFile(String projectId, String filename, ServerHttpResponse response);
    Mono<Void> deleteProject(String projectId);
}
