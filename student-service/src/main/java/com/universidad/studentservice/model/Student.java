package com.universidad.studentservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "students")
@CompoundIndex(def = "{'userId': 1, 'lineaInvestigacionId': 1}", name = "user_linea_index")
public class Student {

    @Id
    private String studentId;

    @NotBlank(message = "El userId es obligatorio")
    private String userId; // Referencia al usuario en el servicio de autenticación

    @NotBlank(message = "El programa es obligatorio")
    @Size(min = 3, max = 100, message = "El programa debe tener entre 3 y 100 caracteres")
    private String programa; // Programa académico

    @NotBlank(message = "La línea de investigación es obligatoria")
    @Size(min = 3, max = 50, message = "La línea de investigación debe tener entre 3 y 50 caracteres")
    private String lineaInvestigacionId; // Referencia a la línea de investigación

    @NotNull(message = "La etapa de inscripción es obligatoria")
    private EnrollmentStage etapaInscripcion;

    // Inicializa la lista de pagos como vacía si no se proporciona
    private List<String> pagos = new ArrayList<>();

    private String proyectoId; // Referencia al proyecto asignado

    private String grupoId; // Referencia al grupo, si aplica

    // Campos para almacenar las fechas de creación y modificación automáticas
    private LocalDateTime createdAt; // Fecha de creación

    private LocalDateTime updatedAt; // Fecha de última modificación
}
