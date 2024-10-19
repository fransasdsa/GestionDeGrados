// src/main/java/com/universidad/project/config/JwtAuthenticationManager.java
package com.universidad.project.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.universidad.project.security.JwtAuthenticationToken;

import javax.crypto.SecretKey;
import java.util.Collections;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final SecretKey key;

    public JwtAuthenticationManager(@Value("${jwt.secret}") String secret) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret key must be at least 32 characters long.");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();

            String userId = claims.getSubject();
            String role = claims.get("role", String.class);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            Authentication auth = new JwtAuthenticationToken(userId, authToken, Collections.singletonList(authority));
            return Mono.just(auth);
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}
