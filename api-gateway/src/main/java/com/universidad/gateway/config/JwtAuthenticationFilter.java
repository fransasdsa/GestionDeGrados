package com.universidad.gateway.config;

import com.universidad.gateway.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration // Se debe cambiar a @Component para que funcione como filtro
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.getClaimsFromToken(token);
                // Agregar información al header de la solicitud
                exchange.getRequest().mutate().header("userId", claims.getSubject());
                exchange.getRequest().mutate().header("role", claims.get("role", String.class));
            } catch (Exception e) {
                // Token inválido
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            // Si la ruta no es pública y no tiene token, rechazar
            String path = exchange.getRequest().getURI().getPath();
            if (!path.startsWith("/api/v1/auth")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        // Continuar con el siguiente filtro en la cadena
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Prioridad alta
    }
}
