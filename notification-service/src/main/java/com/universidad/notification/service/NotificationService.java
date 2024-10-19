package com.universidad.notification.service;

import com.universidad.notification.model.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {
    Mono<Notification> createNotification(Notification notification);
    Mono<Notification> updateNotification(String notificationId, Notification notification);
    Mono<Notification> findByNotificationId(String notificationId);
    Flux<Notification> findByUserId(String userId);
    Flux<Notification> findAll();
    Mono<Void> deleteNotification(String notificationId);

    // Métodos adicionales
    Mono<Notification> sendNotification(Notification notification);
    Flux<Notification> findPendingNotifications();
}
