package com.universidad.researchline.service;

import com.universidad.researchline.model.ResearchLine;
import com.universidad.researchline.repository.ResearchLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResearchLineServiceImpl implements ResearchLineService {

    private final ResearchLineRepository researchLineRepository;

    @Override
    public Mono<ResearchLine> createResearchLine(ResearchLine researchLine) {
        researchLine.setFechaCreacion(LocalDateTime.now());
        researchLine.setFechaActualizacion(LocalDateTime.now());
        return researchLineRepository.save(researchLine);
    }

    @Override
    public Mono<ResearchLine> updateResearchLine(String researchLineId, ResearchLine researchLine) {
        return researchLineRepository.findById(researchLineId)
                .flatMap(existingLine -> {
                    existingLine.setNombre(researchLine.getNombre());
                    existingLine.setDescripcion(researchLine.getDescripcion());
                    existingLine.setAsesores(researchLine.getAsesores());
                    existingLine.setProyectos(researchLine.getProyectos());
                    existingLine.setFechaActualizacion(LocalDateTime.now());
                    return researchLineRepository.save(existingLine);
                });
    }

    @Override
    public Mono<ResearchLine> findByResearchLineId(String researchLineId) {
        return researchLineRepository.findById(researchLineId);
    }

    @Override
    public Flux<ResearchLine> findByNombre(String nombre) {
        return researchLineRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Flux<ResearchLine> findAll() {
        return researchLineRepository.findAll();
    }

    @Override
    public Mono<Void> deleteResearchLine(String researchLineId) {
        return researchLineRepository.deleteById(researchLineId);
    }
}
