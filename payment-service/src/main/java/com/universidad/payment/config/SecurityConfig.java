// src/main/java/com/universidad/payment/config/SecurityConfig.java
package com.universidad.payment.config;

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
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        // Excepciones para Swagger
                        .pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Protecciones por roles
                        .pathMatchers(HttpMethod.POST, "/api/v1/payments").hasRole("ESTUDIANTE")
                        .pathMatchers("/api/v1/payments/**").authenticated()
                        .anyExchange().permitAll()
                )
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .build();
    }
}
