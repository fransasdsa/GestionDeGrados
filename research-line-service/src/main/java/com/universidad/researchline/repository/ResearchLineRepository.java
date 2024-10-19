// src/main/java/com/universidad/researchline/repository/ResearchLineRepository.java
package com.universidad.researchline.repository;

import com.universidad.researchline.model.ResearchLine;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ResearchLineRepository extends ReactiveMongoRepository<ResearchLine, String> {
    Flux<ResearchLine> findByNombreContainingIgnoreCase(String nombre);
}
