package com.universidad.researchline.service;

import com.universidad.researchline.model.ResearchLine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResearchLineService {
    Mono<ResearchLine> createResearchLine(ResearchLine researchLine);
    Mono<ResearchLine> updateResearchLine(String researchLineId, ResearchLine researchLine);
    Mono<ResearchLine> findByResearchLineId(String researchLineId);
    Flux<ResearchLine> findByNombre(String nombre);
    Flux<ResearchLine> findAll();
    Mono<Void> deleteResearchLine(String researchLineId);
}
