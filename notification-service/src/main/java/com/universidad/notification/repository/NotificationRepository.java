// src/main/java/com/universidad/notification/repository/NotificationRepository.java
package com.universidad.notification.repository;

import com.universidad.notification.model.Notification;
import com.universidad.notification.model.NotificationStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {
    Flux<Notification> findByUserId(String userId);
    Flux<Notification> findByEstado(NotificationStatus estado);
}
