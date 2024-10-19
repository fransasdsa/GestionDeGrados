package com.universidad.advisor.repository;

import com.universidad.advisor.model.Advisor;
import com.universidad.advisor.model.AvailabilityStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdvisorRepository extends ReactiveMongoRepository<Advisor, String> {
    Mono<Advisor> findByUserId(String userId);
    Flux<Advisor> findByEspecialidadesContaining(String lineaInvestigacionId);
    Flux<Advisor> findByDisponibilidad(AvailabilityStatus disponibilidad);
}
