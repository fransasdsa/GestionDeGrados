// src/main/java/com/universidad/researchline/model/ResearchLine.java
package com.universidad.researchline.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "research_lines")
public class ResearchLine {

    @Id
    private String researchLineId;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;


    private List<String> asesores; // Lista de IDs de asesores asociados

    private List<String> proyectos; // Lista de IDs de proyectos asociados

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;
}
