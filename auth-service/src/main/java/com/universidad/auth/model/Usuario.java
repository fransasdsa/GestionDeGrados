// src/main/java/com/universidad/auth/model/Usuario.java
package com.universidad.auth.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String usuarioId;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Correo electrónico no válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    private Rol rol;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;
}
