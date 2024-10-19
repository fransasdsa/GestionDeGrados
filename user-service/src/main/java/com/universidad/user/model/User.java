package com.universidad.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String userId;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Role rol;

    private List<Permission> permisos;

    // Agregar campos para fechas de creación y actualización
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
