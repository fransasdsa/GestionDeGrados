// src/main/java/com/universidad/user/repository/UserRepository.java
package com.universidad.user.repository;

import com.universidad.user.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByEmail(String email); // Este método ya busca por email
}
