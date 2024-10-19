package com.universidad.notification.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Document(collection = "notifications")
public class Notification {

    @Id
    private String notificationId;

    @NotBlank(message = "El ID del usuario es obligatorio")
    private String userId;

    @NotNull(message = "El tipo de notificación es obligatorio")
    private NotificationType tipo;

    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fechaEnvio;

    @NotNull(message = "El estado de la notificación es obligatorio")
    private NotificationStatus estado;

    private String detalles; // Detalles adicionales (opcional)
}
