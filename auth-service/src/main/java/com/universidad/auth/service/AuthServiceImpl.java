// src/main/java/com/universidad/auth/service/AuthServiceImpl.java
package com.universidad.auth.service;

import com.universidad.auth.model.LoginRequest;
import com.universidad.auth.model.LoginResponse;
import com.universidad.auth.model.Usuario;
import com.universidad.auth.repository.UsuarioRepository;
import com.universidad.auth.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Override
    public Mono<Usuario> registerUser(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setFechaActualizacion(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    @Override
    public Mono<LoginResponse> loginUser(LoginRequest loginRequest) {
        return usuarioRepository.findByEmail(loginRequest.getEmail())
                .flatMap(usuario -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                        String token = jwtUtil.generateToken(usuario);
                        LoginResponse response = new LoginResponse();
                        response.setToken(token);
                        response.setUsuarioId(usuario.getUsuarioId());
                        response.setNombreCompleto(usuario.getNombre() + " " + usuario.getApellido());
                        response.setRol(usuario.getRol());
                        return Mono.just(response);
                    } else {
                        return Mono.error(new RuntimeException("Credenciales incorrectas"));
                    }
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")));
    }

    @Override
    public Mono<Usuario> findByUsuarioId(String usuarioId) {
        return usuarioRepository.findById(usuarioId);
    }

    @Override
    public Mono<Usuario> updateUsuario(String usuarioId, Usuario usuario) {
        return usuarioRepository.findById(usuarioId)
                .flatMap(existingUsuario -> {
                    existingUsuario.setNombre(usuario.getNombre());
                    existingUsuario.setApellido(usuario.getApellido());
                    existingUsuario.setEmail(usuario.getEmail());
                    existingUsuario.setRol(usuario.getRol());
                    existingUsuario.setFechaActualizacion(LocalDateTime.now());
                    return usuarioRepository.save(existingUsuario);
                });
    }

    @Override
    public Mono<Void> deleteUsuario(String usuarioId) {
        return usuarioRepository.deleteById(usuarioId);
    }
}
