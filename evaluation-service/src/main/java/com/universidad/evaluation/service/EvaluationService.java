// src/main/java/com/universidad/evaluation/service/EvaluationService.java
package com.universidad.evaluation.service;

import com.universidad.evaluation.model.Evaluation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EvaluationService {
    Mono<Evaluation> createEvaluation(Evaluation evaluation);
    Mono<Evaluation> updateEvaluation(String evaluationId, Evaluation evaluation);
    Mono<Evaluation> findByEvaluationId(String evaluationId);
    Flux<Evaluation> findByProjectId(String projectId);
    Flux<Evaluation> findByEvaluatorId(String evaluatorId);
    Flux<Evaluation> findAll();
    Mono<Void> deleteEvaluation(String evaluationId);
}
