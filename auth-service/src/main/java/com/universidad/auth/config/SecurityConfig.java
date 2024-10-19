package com.universidad.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ReactiveUserDetailsService userDetailsService) {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si es necesario
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // Deshabilitar autenticación básica
                .formLogin(formLogin -> formLogin.disable()) // Deshabilitar login por formulario
                .authenticationManager(authenticationManager(userDetailsService))
                .securityContextRepository(securityContextRepository())
                .build();
    }

    @Bean
    public UserDetailsRepositoryReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }

    @Bean
    public JwtSecurityContextRepository securityContextRepository() {
        return new JwtSecurityContextRepository(jwtUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
