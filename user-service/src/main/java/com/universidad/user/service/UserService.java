// src/main/java/com/universidad/user/service/UserService.java
package com.universidad.user.service;

import com.universidad.user.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> createUser(User user);
    Mono<User> updateUser(String userId, User user);
    Mono<User> findByUserId(String userId);
    Mono<User> findByEmail(String email);
    Flux<User> findAll();
    Mono<Void> deleteUser(String userId);
}
