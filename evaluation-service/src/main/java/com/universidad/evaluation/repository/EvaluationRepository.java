// src/main/java/com/universidad/evaluation/repository/EvaluationRepository.java
package com.universidad.evaluation.repository;

import com.universidad.evaluation.model.Evaluation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface EvaluationRepository extends ReactiveMongoRepository<Evaluation, String> {
    Flux<Evaluation> findByProjectId(String projectId);
    Flux<Evaluation> findByEvaluatorId(String evaluatorId);
}
