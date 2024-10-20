package com.universidad.project.config;

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
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        // Excluir rutas de Swagger
                        .pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                        // Rutas protegidas por roles
                        .pathMatchers(HttpMethod.POST, "/api/v1/projects").hasRole("ESTUDIANTE")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/projects/**").hasAnyRole("ESTUDIANTE", "ASESOR", "COORDINADOR", "ADMINISTRADOR")
                        .pathMatchers(HttpMethod.GET, "/api/v1/projects/**").hasAnyRole("ESTUDIANTE", "ASESOR", "EVALUADOR", "COORDINADOR", "ADMINISTRADOR")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/projects/**").hasAnyRole("COORDINADOR", "ADMINISTRADOR")
                        // Todas las demás rutas requieren autenticación
                        .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
