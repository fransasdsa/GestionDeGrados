// src/main/java/com/universidad/user/config/SecurityConfig.java
package com.universidad.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationManager authenticationManager;
    private final JwtSecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF
                .authorizeExchange(exchanges -> exchanges
                        // Protecciones por roles
                        .pathMatchers(HttpMethod.POST, "/api/v1/users").hasRole("ADMINISTRADOR")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMINISTRADOR")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("/api/v1/users/**").authenticated()
                        .anyExchange().permitAll()  // Cualquier otra ruta está permitida sin autenticación
                )
                .authenticationManager(authenticationManager)  // Autenticación JWT
                .securityContextRepository(securityContextRepository)  // Configuración del contexto JWT
                .build();
    }

    // Agrega el PasswordEncoder para la codificación de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
