package com.universidad.payment.repository;

import com.universidad.payment.model.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {
    Flux<Payment> findByStudentId(String studentId);
}
