// src/main/java/com/universidad/student/config/SecurityConfig.java
package com.universidad.studentservice.config;

import com.universidad.studentservice.security.JwtAuthenticationManager;
import com.universidad.studentservice.security.JwtSecurityContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
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
                        // Excepciones para Swagger
                        .pathMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/students").hasRole("ESTUDIANTE")
                        .pathMatchers("/api/v1/students/**").authenticated()
                        .anyExchange().permitAll()
                )
                .authenticationManager(authenticationManager)  // Autenticación JWT
                .securityContextRepository(securityContextRepository)  // Configuración JWT
                .build();
    }
}
