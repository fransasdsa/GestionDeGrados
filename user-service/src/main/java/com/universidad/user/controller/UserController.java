// src/main/java/com/universidad/user/controller/UserController.java
package com.universidad.user.controller;

import com.universidad.user.model.User;
import com.universidad.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<User> createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ESTUDIANTE', 'ASESOR', 'EVALUADOR', 'ADMINISTRADOR', 'COORDINADOR')")
    public Mono<User> getUserById(@PathVariable String userId) {
        return userService.findByUserId(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Flux<User> getAllUsers() {
        return userService.findAll();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<User> updateUser(@PathVariable String userId, @Valid @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<Void> deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }
}
