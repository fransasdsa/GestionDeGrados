// src/main/java/com/universidad/evaluation/controller/EvaluationController.java
package com.universidad.evaluation.controller;

import com.universidad.evaluation.model.Evaluation;
import com.universidad.evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('EVALUADOR')")
    public Mono<Evaluation> createEvaluation(@Valid @RequestBody Evaluation evaluation) {
        return evaluationService.createEvaluation(evaluation);
    }

    @GetMapping("/{evaluationId}")
    @PreAuthorize("hasAnyRole('EVALUADOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Evaluation> getEvaluationById(@PathVariable String evaluationId) {
        return evaluationService.findByEvaluationId(evaluationId);
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'EVALUADOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Evaluation> getEvaluationsByProjectId(@PathVariable String projectId) {
        return evaluationService.findByProjectId(projectId);
    }

    @GetMapping("/evaluator/{evaluatorId}")
    @PreAuthorize("hasAnyRole('EVALUADOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Evaluation> getEvaluationsByEvaluatorId(@PathVariable String evaluatorId) {
        return evaluationService.findByEvaluatorId(evaluatorId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Evaluation> getAllEvaluations() {
        return evaluationService.findAll();
    }

    @PutMapping("/{evaluationId}")
    @PreAuthorize("hasAnyRole('EVALUADOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Evaluation> updateEvaluation(@PathVariable String evaluationId, @Valid @RequestBody Evaluation evaluation) {
        return evaluationService.updateEvaluation(evaluationId, evaluation);
    }

    @DeleteMapping("/{evaluationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('EVALUADOR', 'COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Void> deleteEvaluation(@PathVariable String evaluationId) {
        return evaluationService.deleteEvaluation(evaluationId);
    }
}
