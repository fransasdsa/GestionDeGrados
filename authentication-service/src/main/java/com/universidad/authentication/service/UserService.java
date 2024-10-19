package com.universidad.authentication.service;

import com.universidad.authentication.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> registerUser(User user);
    Mono<User> findByEmail(String email);
}
