package com.universidad.authentication.config;

import com.universidad.authentication.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/auth/register", "/api/v1/auth/login", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager())
                .securityContextRepository(new SecurityContextRepository(jwtService))
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return authentication -> {
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return Mono.just(authentication);
            }
            return Mono.empty();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
