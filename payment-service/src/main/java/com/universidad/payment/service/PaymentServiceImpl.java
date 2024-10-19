package com.universidad.payment.service;

import com.universidad.payment.model.Payment;
import com.universidad.payment.model.PaymentStatus;
import com.universidad.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Mono<Payment> createPayment(Payment payment) {
        payment.setFechaPago(LocalDateTime.now()); // Asigna la fecha automáticamente
        return paymentRepository.save(payment);
    }

    @Override
    public Mono<Payment> updatePayment(String paymentId, Payment payment) {
        return paymentRepository.findById(paymentId)
                .flatMap(existingPayment -> {
                    if(existingPayment.getEstado() == PaymentStatus.COMPLETADO) {
                        return Mono.error(new IllegalArgumentException("No se puede modificar un pago completado"));
                    }
                    existingPayment.setMonto(payment.getMonto());
                    existingPayment.setMetodoPago(payment.getMetodoPago());
                    existingPayment.setFechaPago(payment.getFechaPago());
                    existingPayment.setEstado(payment.getEstado());
                    existingPayment.setTransaccionId(payment.getTransaccionId());
                    existingPayment.setDescripcion(payment.getDescripcion());
                    return paymentRepository.save(existingPayment);
                });
    }

    @Override
    public Mono<Payment> findByPaymentId(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public Flux<Payment> findByStudentId(String studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    @Override
    public Flux<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Mono<Void> deletePayment(String paymentId) {
        return paymentRepository.deleteById(paymentId);
    }
}
