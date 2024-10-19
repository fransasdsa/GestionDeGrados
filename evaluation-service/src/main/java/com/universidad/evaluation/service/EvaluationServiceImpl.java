package com.universidad.evaluation.service;

import com.universidad.evaluation.model.Evaluation;
import com.universidad.evaluation.model.EvaluationStatus;
import com.universidad.evaluation.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Override
    public Mono<Evaluation> createEvaluation(Evaluation evaluation) {
        evaluation.setEstado(EvaluationStatus.PENDIENTE);
        evaluation.setFechaCreacion(LocalDateTime.now());
        evaluation.setFechaActualizacion(LocalDateTime.now());
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Mono<Evaluation> updateEvaluation(String evaluationId, Evaluation evaluation) {
        return evaluationRepository.findById(evaluationId)
                .flatMap(existingEvaluation -> {
                    existingEvaluation.setCalificacion(evaluation.getCalificacion());
                    existingEvaluation.setComentarios(evaluation.getComentarios());
                    existingEvaluation.setEstado(evaluation.getEstado());
                    existingEvaluation.setRubrica(evaluation.getRubrica());
                    existingEvaluation.setFechaActualizacion(LocalDateTime.now()); // Actualizamos la fecha
                    return evaluationRepository.save(existingEvaluation);
                });
    }

    @Override
    public Mono<Evaluation> findByEvaluationId(String evaluationId) {
        return evaluationRepository.findById(evaluationId);
    }

    @Override
    public Flux<Evaluation> findByProjectId(String projectId) {
        return evaluationRepository.findByProjectId(projectId);
    }

    @Override
    public Flux<Evaluation> findByEvaluatorId(String evaluatorId) {
        return evaluationRepository.findByEvaluatorId(evaluatorId);
    }

    @Override
    public Flux<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }

    @Override
    public Mono<Void> deleteEvaluation(String evaluationId) {
        return evaluationRepository.deleteById(evaluationId);
    }
}
