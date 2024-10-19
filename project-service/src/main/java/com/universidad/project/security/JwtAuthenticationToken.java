// src/main/java/com/universidad/project/security/JwtAuthenticationToken.java
package com.universidad.project.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private String token;

    public JwtAuthenticationToken(Object principal, String token) {
        super(null);
        this.principal = principal;
        this.token = token;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
