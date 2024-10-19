// src/main/java/com/universidad/auth/model/LoginRequest.java
package com.universidad.auth.model;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "El correo electrónico es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
