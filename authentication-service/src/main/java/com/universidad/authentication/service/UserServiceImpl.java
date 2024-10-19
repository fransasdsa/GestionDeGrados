package com.universidad.authentication.service;

import com.universidad.authentication.exception.EmailAlreadyExistsException;
import com.universidad.authentication.model.User;
import com.universidad.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> registerUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existingUser -> Mono.<User>error(new EmailAlreadyExistsException("El email ya está registrado")))
                .switchIfEmpty(Mono.defer(() -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setFechaRegistro(LocalDateTime.now());
                    return userRepository.save(user);
                }));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
