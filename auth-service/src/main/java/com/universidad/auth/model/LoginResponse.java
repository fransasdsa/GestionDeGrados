// src/main/java/com/universidad/auth/model/LoginResponse.java
package com.universidad.auth.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String usuarioId;
    private String nombreCompleto;
    private Rol rol;
}
