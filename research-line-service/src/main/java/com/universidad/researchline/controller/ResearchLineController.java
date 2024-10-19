package com.universidad.researchline.controller;

import com.universidad.researchline.model.ResearchLine;
import com.universidad.researchline.service.ResearchLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/research-lines")
@RequiredArgsConstructor
public class ResearchLineController {

    private final ResearchLineService researchLineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<ResearchLine> createResearchLine(@Valid @RequestBody ResearchLine researchLine) {
        return researchLineService.createResearchLine(researchLine);
    }

    @GetMapping("/{researchLineId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'EVALUADOR', 'ADMINISTRADOR', 'COORDINADOR')")
    public Mono<ResearchLine> getResearchLineById(@PathVariable String researchLineId) {
        return researchLineService.findByResearchLineId(researchLineId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'EVALUADOR', 'ADMINISTRADOR', 'COORDINADOR')")
    public Flux<ResearchLine> getAllResearchLines() {
        return researchLineService.findAll();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'EVALUADOR', 'ADMINISTRADOR', 'COORDINADOR')")
    public Flux<ResearchLine> searchResearchLines(@RequestParam String nombre) {
        return researchLineService.findByNombre(nombre);
    }

    @PutMapping("/{researchLineId}")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR')")
    public Mono<ResearchLine> updateResearchLine(@PathVariable String researchLineId, @Valid @RequestBody ResearchLine researchLine) {
        return researchLineService.updateResearchLine(researchLineId, researchLine);
    }

    @DeleteMapping("/{researchLineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Void> deleteResearchLine(@PathVariable String researchLineId) {
        return researchLineService.deleteResearchLine(researchLineId);
    }
}
