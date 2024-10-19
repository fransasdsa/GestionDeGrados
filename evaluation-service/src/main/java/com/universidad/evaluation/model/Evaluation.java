package com.universidad.evaluation.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "evaluations")
public class Evaluation {

    @Id
    private String evaluationId;

    @NotBlank(message = "El ID del proyecto es obligatorio")
    private String projectId;

    @NotBlank(message = "El ID del evaluador es obligatorio")
    private String evaluatorId;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 0, message = "La calificación mínima es 0")
    @Max(value = 100, message = "La calificación máxima es 100")
    private Integer calificacion;

    @NotBlank(message = "Los comentarios son obligatorios")
    private String comentarios;

    @NotNull(message = "La fecha de evaluación es obligatoria")
    private LocalDateTime fecha;

    @NotNull(message = "La rúbrica es obligatoria")
    @Size(min = 1, message = "Debe haber al menos un criterio en la rúbrica")
    private Map<@NotBlank String, @Min(0) @Max(10) Integer> rubrica; // Clave: criterio, Valor: puntuación

    @NotNull(message = "El estado de la evaluación es obligatorio")
    private EvaluationStatus estado;

    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDateTime fechaCreacion;

    @NotNull(message = "La fecha de actualización es obligatoria")
    private LocalDateTime fechaActualizacion;
}
