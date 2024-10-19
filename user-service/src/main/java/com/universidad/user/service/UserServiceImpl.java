package com.universidad.user.service;

import com.universidad.user.model.User;
import com.universidad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFechaCreacion(LocalDateTime.now());
        user.setFechaActualizacion(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateUser(String userId, User user) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> {
                    existingUser.setNombre(user.getNombre());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setRol(user.getRol());
                    existingUser.setPermisos(user.getPermisos());
                    existingUser.setFechaActualizacion(LocalDateTime.now());  // Actualizamos la fecha
                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    }
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public Mono<User> findByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<Void> deleteUser(String userId) {
        return userRepository.deleteById(userId);
    }
}
