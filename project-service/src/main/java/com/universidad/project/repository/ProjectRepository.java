// src/main/java/com/universidad/project/repository/ProjectRepository.java
package com.universidad.project.repository;

import com.universidad.project.model.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
    Flux<Project> findByEstudiantesContains(String estudianteId);

    Flux<Project> findByAsesorId(String asesorId);
    Flux<Project> findByResearchLineId(String researchLineId);
}
