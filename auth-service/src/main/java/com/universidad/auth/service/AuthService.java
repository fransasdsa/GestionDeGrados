// src/main/java/com/universidad/auth/service/AuthService.java
package com.universidad.auth.service;

import com.universidad.auth.model.LoginRequest;
import com.universidad.auth.model.LoginResponse;
import com.universidad.auth.model.Usuario;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<Usuario> registerUser(Usuario usuario);
    Mono<LoginResponse> loginUser(LoginRequest loginRequest);
    Mono<Usuario> findByUsuarioId(String usuarioId);
    Mono<Usuario> updateUsuario(String usuarioId, Usuario usuario);
    Mono<Void> deleteUsuario(String usuarioId);
}
