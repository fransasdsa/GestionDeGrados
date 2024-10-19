package com.universidad.notification.controller;

import com.universidad.notification.model.Notification;
import com.universidad.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COORDINADOR')")
    public Mono<Notification> createNotification(@Valid @RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'EVALUADOR', 'ADMINISTRADOR', 'COORDINADOR')")
    public Mono<Notification> getNotificationById(@PathVariable String notificationId) {
        return notificationService.findByNotificationId(notificationId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'EVALUADOR', 'ADMINISTRADOR', 'COORDINADOR')")
    public Flux<Notification> getNotificationsByUserId(@PathVariable String userId) {
        return notificationService.findByUserId(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Flux<Notification> getAllNotifications() {
        return notificationService.findAll();
    }

    @PutMapping("/{notificationId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'COORDINADOR')")
    public Mono<Notification> updateNotification(@PathVariable String notificationId, @Valid @RequestBody Notification notification) {
        return notificationService.updateNotification(notificationId, notification);
    }

    @DeleteMapping("/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<Void> deleteNotification(@PathVariable String notificationId) {
        return notificationService.deleteNotification(notificationId);
    }
}
