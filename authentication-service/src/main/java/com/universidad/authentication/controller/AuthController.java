package com.universidad.authentication.controller;

import com.universidad.authentication.exception.InvalidCredentialsException;
import com.universidad.authentication.model.LoginRequest;
import com.universidad.authentication.model.RegistrationResponse;
import com.universidad.authentication.model.TokenResponse;
import com.universidad.authentication.model.User;
import com.universidad.authentication.service.JwtService;
import com.universidad.authentication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RegistrationResponse> register(@Valid @RequestBody User user) {
        return userService.registerUser(user)
                .map(registeredUser -> new RegistrationResponse(registeredUser.getUserId(), registeredUser.getEmail()));
    }

    @PostMapping("/login")
    public Mono<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.findByEmail(loginRequest.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("Credenciales inválidas")))
                .map(user -> {
                    String token = jwtService.generateToken(user.getUserId(), user.getEmail(), user.getRol());
                    return new TokenResponse(token);
                });
    }

    @GetMapping("/me")
    public Mono<User> me(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return userService.findByEmail(jwtService.validateToken(token).get("email", String.class))
                .map(user -> {
                    user.setPassword(null); // No devolver la contraseña
                    return user;
                });
    }
}
