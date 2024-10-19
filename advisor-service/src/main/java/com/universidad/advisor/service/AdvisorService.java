package com.universidad.advisor.service;

import com.universidad.advisor.model.Advisor;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdvisorService {
    Mono<Advisor> createAdvisor(Advisor advisor);
    Mono<Advisor> updateAdvisor(String advisorId, Advisor advisor);
    Mono<Advisor> findByAdvisorId(String advisorId);
    Mono<Advisor> findByUserId(String userId);
    Flux<Advisor> findAll(); // Método sin paginación (anterior)
    Flux<Advisor> findAll(PageRequest pageRequest); // Método con paginación (nuevo)
    Mono<Void> deleteAdvisor(String advisorId);
    Flux<Advisor> findByEspecialidad(String lineaInvestigacionId);
    Flux<Advisor> findAvailableAdvisors();
}
