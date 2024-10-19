package com.universidad.notification.service;

import com.universidad.notification.model.Notification;
import com.universidad.notification.model.NotificationStatus;
import com.universidad.notification.model.NotificationType;
import com.universidad.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    @Override
    public Mono<Notification> createNotification(Notification notification) {
        notification.setFechaEnvio(LocalDateTime.now());
        notification.setEstado(NotificationStatus.PENDIENTE);
        return notificationRepository.save(notification)
                .flatMap(savedNotification -> sendNotification(savedNotification)
                        .thenReturn(savedNotification));
    }

    @Override
    public Mono<Notification> updateNotification(String notificationId, Notification notification) {
        return notificationRepository.findById(notificationId)
                .flatMap(existingNotification -> {
                    existingNotification.setAsunto(notification.getAsunto());
                    existingNotification.setMensaje(notification.getMensaje());
                    existingNotification.setTipo(notification.getTipo());
                    existingNotification.setDetalles(notification.getDetalles());
                    return notificationRepository.save(existingNotification);
                });
    }

    @Override
    public Mono<Notification> findByNotificationId(String notificationId) {
        return notificationRepository.findById(notificationId);
    }

    @Override
    public Flux<Notification> findByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public Flux<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Mono<Void> deleteNotification(String notificationId) {
        return notificationRepository.deleteById(notificationId);
    }

    @Override
    public Mono<Notification> sendNotification(Notification notification) {
        if (notification.getTipo() == NotificationType.EMAIL) {
            return sendEmailNotification(notification)
                    .then(Mono.defer(() -> {
                        notification.setEstado(NotificationStatus.ENVIADA);
                        return notificationRepository.save(notification);
                    }))
                    .onErrorResume(e -> {
                        notification.setEstado(NotificationStatus.FALLIDA);
                        return notificationRepository.save(notification)
                                .then(Mono.error(e));
                    });
        }
        // Implementar otros tipos de notificación (SMS, PUSH)
        return Mono.just(notification);
    }

    private Mono<Void> sendEmailNotification(Notification notification) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("destinatario@example.com"); // Cambia esto para obtener el correo del usuario real
            message.setSubject(notification.getAsunto());
            message.setText(notification.getMensaje());
            mailSender.send(message);
        });
    }

    @Override
    public Flux<Notification> findPendingNotifications() {
        return notificationRepository.findByEstado(NotificationStatus.PENDIENTE);
    }
}
