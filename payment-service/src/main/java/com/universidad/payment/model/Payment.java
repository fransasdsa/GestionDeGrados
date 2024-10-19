package com.universidad.payment.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Document(collection = "payments")
public class Payment {

    @Id
    private String paymentId;

    @NotBlank(message = "El ID del estudiante es obligatorio")
    private String studentId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago; // Ejemplo: "Tarjeta de Crédito", "PayPal"

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDateTime fechaPago;

    @NotNull(message = "El estado del pago es obligatorio")
    private PaymentStatus estado;

    private String transaccionId; // ID de la transacción en la pasarela de pago

    private String descripcion;
}
