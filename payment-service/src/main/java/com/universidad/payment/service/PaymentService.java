package com.universidad.payment.service;

import com.universidad.payment.model.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {
    Mono<Payment> createPayment(Payment payment);
    Mono<Payment> updatePayment(String paymentId, Payment payment);
    Mono<Payment> findByPaymentId(String paymentId);
    Flux<Payment> findByStudentId(String studentId);
    Flux<Payment> findAll();
    Mono<Void> deletePayment(String paymentId);
}
