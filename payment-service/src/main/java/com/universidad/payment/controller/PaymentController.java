package com.universidad.payment.controller;

import com.universidad.payment.model.Payment;
import com.universidad.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public Mono<Payment> createPayment(@Valid @RequestBody Payment payment) {
        if(payment.getMonto() <= 0) {
            return Mono.error(new IllegalArgumentException("El monto debe ser mayor que 0."));
        }
        return paymentService.createPayment(payment);
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ADMINISTRADOR')")
    public Mono<Payment> getPaymentById(@PathVariable String paymentId) {
        return paymentService.findByPaymentId(paymentId);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ADMINISTRADOR')")
    public Flux<Payment> getPaymentsByStudentId(@PathVariable String studentId) {
        return paymentService.findByStudentId(studentId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Flux<Payment> getAllPayments() {
        return paymentService.findAll();
    }

    @PutMapping("/{paymentId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<Payment> updatePayment(@PathVariable String paymentId, @Valid @RequestBody Payment payment) {
        return paymentService.updatePayment(paymentId, payment);
    }

    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<Void> deletePayment(@PathVariable String paymentId) {
        return paymentService.deletePayment(paymentId);
    }
}
