// src/main/java/com/universidad/auth/repository/UsuarioRepository.java
package com.universidad.auth.repository;

import com.universidad.auth.model.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String> {
    Mono<Usuario> findByEmail(String email);
}
