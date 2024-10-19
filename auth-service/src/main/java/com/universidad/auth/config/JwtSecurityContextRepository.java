// src/main/java/com/universidad/auth/config/JwtSecurityContextRepository.java
package com.universidad.auth.config;

import com.universidad.auth.security.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        // No es necesario implementar para JWT
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .flatMap(authHeader -> {
                    String authToken = authHeader.substring(7);
                    try {
                        jwtUtil.getClaimsFromToken(authToken);
                        return Mono.just(new SecurityContextImpl(new JwtAuthenticationToken(authToken)));
                    } catch (Exception e) {
                        return Mono.empty();
                    }
                });
    }
}
