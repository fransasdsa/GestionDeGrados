package com.universidad.advisor.controller;

import com.universidad.advisor.model.Advisor;
import com.universidad.advisor.service.AdvisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/advisors")
@RequiredArgsConstructor
public class AdvisorController {

    private final AdvisorService advisorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ASESOR')")
    public Mono<Advisor> createAdvisor(@Valid @RequestBody Advisor advisor) {
        return advisorService.createAdvisor(advisor);
    }

    @GetMapping("/{advisorId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'COORDINADOR', 'ADMINISTRADOR', 'ASESOR')")
    public Mono<Advisor> getAdvisorById(@PathVariable String advisorId) {
        return advisorService.findByAdvisorId(advisorId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR', 'ASESOR')")
    public Mono<Advisor> getAdvisorByUserId(@PathVariable String userId) {
        return advisorService.findByUserId(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Flux<Advisor> getAllAdvisors(@RequestParam("page") int page, @RequestParam("size") int size) {
        return advisorService.findAll(PageRequest.of(page, size));
    }

    @PutMapping("/{advisorId}")
    @PreAuthorize("hasRole('ASESOR')")
    public Mono<Advisor> updateAdvisor(@PathVariable String advisorId, @Valid @RequestBody Advisor advisor) {
        return advisorService.updateAdvisor(advisorId, advisor);
    }

    @DeleteMapping("/{advisorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<Void> deleteAdvisor(@PathVariable String advisorId) {
        return advisorService.deleteAdvisor(advisorId);
    }

    @GetMapping("/especialidad/{lineaInvestigacionId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Advisor> getAdvisorsByEspecialidad(@PathVariable String lineaInvestigacionId) {
        return advisorService.findByEspecialidad(lineaInvestigacionId);
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'COORDINADOR', 'ADMINISTRADOR')")
    public Flux<Advisor> getAvailableAdvisors() {
        return advisorService.findAvailableAdvisors();
    }
}
