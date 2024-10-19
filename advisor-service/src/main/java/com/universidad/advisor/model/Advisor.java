// src/main/java/com/universidad/advisor/model/Advisor.java
package com.universidad.advisor.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.util.List;

@Data
@Document(collection = "advisors")
public class Advisor {

    @Id
    private String advisorId;

    @NotBlank(message = "El userId es obligatorio")
    private String userId; // Referencia al usuario en el servicio de autenticación

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    private String email;

    @NotBlank(message = "El perfil profesional es obligatorio")
    private String perfilProfesional;

    @NotNull(message = "Las especialidades son obligatorias")
    private List<String> especialidades; // Lista de IDs de líneas de investigación

    private Double calificacionEstrellas = 0.0; // Promedio de calificaciones

    private Integer proyectosActuales = 0; // Número de proyectos en curso

    @NotNull
    private AvailabilityStatus disponibilidad = AvailabilityStatus.DISPONIBLE;

    private List<String> proyectosAsignados; // Lista de IDs de proyectos asignados
}
