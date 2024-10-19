package com.universidad.advisor.service;

import com.universidad.advisor.exception.AdvisorNotFoundException;
import com.universidad.advisor.model.Advisor;
import com.universidad.advisor.model.AvailabilityStatus;
import com.universidad.advisor.repository.AdvisorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AdvisorServiceImpl implements AdvisorService {

    private final AdvisorRepository advisorRepository;

    @Override
    public Mono<Advisor> createAdvisor(Advisor advisor) {
        return advisorRepository.save(advisor);
    }

    @Override
    public Mono<Advisor> updateAdvisor(String advisorId, Advisor advisor) {
        return advisorRepository.findById(advisorId)
                .flatMap(existingAdvisor -> {
                    existingAdvisor.setPerfilProfesional(advisor.getPerfilProfesional());
                    existingAdvisor.setEspecialidades(advisor.getEspecialidades());
                    existingAdvisor.setDisponibilidad(advisor.getDisponibilidad());
                    existingAdvisor.setProyectosActuales(advisor.getProyectosActuales());
                    existingAdvisor.setProyectosAsignados(advisor.getProyectosAsignados());
                    existingAdvisor.setCalificacionEstrellas(advisor.getCalificacionEstrellas());
                    return advisorRepository.save(existingAdvisor);
                });
    }

    @Override
    public Mono<Advisor> findByAdvisorId(String advisorId) {
        return advisorRepository.findById(advisorId)
                .switchIfEmpty(Mono.error(new AdvisorNotFoundException("Asesor no encontrado con ID: " + advisorId)));
    }

    @Override
    public Mono<Advisor> findByUserId(String userId) {
        return advisorRepository.findByUserId(userId);
    }

    @Override
    public Flux<Advisor> findAll() {
        return advisorRepository.findAll();
    }

    @Override
    public Flux<Advisor> findAll(PageRequest pageRequest) {
        int page = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        return advisorRepository.findAll()
                .skip((long) page * size)
                .take(size);
    }

    @Override
    public Mono<Void> deleteAdvisor(String advisorId) {
        return advisorRepository.deleteById(advisorId);
    }

    @Override
    public Flux<Advisor> findByEspecialidad(String lineaInvestigacionId) {
        return advisorRepository.findByEspecialidadesContaining(lineaInvestigacionId);
    }

    @Override
    public Flux<Advisor> findAvailableAdvisors() {
        return advisorRepository.findByDisponibilidad(AvailabilityStatus.DISPONIBLE);
    }
}
