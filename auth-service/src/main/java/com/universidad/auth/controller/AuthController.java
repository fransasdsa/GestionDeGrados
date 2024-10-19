// src/main/java/com/universidad/auth/controller/AuthController.java
package com.universidad.auth.controller;

import com.universidad.auth.model.LoginRequest;
import com.universidad.auth.model.LoginResponse;
import com.universidad.auth.model.Usuario;
import com.universidad.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Usuario> registerUser(@Valid @RequestBody Usuario usuario) {
        return authService.registerUser(usuario);
    }

    @PostMapping("/login")
    public Mono<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @GetMapping("/users/{usuarioId}")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Usuario> getUsuarioById(@PathVariable String usuarioId) {
        return authService.findByUsuarioId(usuarioId);
    }

    @PutMapping("/users/{usuarioId}")
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Usuario> updateUsuario(@PathVariable String usuarioId, @Valid @RequestBody Usuario usuario) {
        return authService.updateUsuario(usuarioId, usuario);
    }

    @DeleteMapping("/users/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('COORDINADOR', 'ADMINISTRADOR')")
    public Mono<Void> deleteUsuario(@PathVariable String usuarioId) {
        return authService.deleteUsuario(usuarioId);
    }
}
