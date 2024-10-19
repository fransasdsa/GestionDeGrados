package com.universidad.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "projects")
public class Project {

    @Id
    private String projectId;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La lista de estudiantes es obligatoria")
    private List<String> estudiantes; // Lista de IDs de estudiantes

    @NotBlank(message = "El ID del asesor es obligatorio")
    private String asesorId; // ID del asesor asignado

    @NotBlank(message = "El ID de la línea de investigación es obligatorio")
    private String researchLineId;

    @NotNull(message = "El estado del proyecto es obligatorio")
    private ProjectStatus estado;

    private List<String> archivos; // Lista de nombres o URLs de los archivos subidos

    @NotNull(message = "La etapa del proyecto es obligatoria")
    private ProjectStage etapa;

    private List<Comment> comentarios;

    private List<DocumentMetadata> documentos; // Lista de documentos asociados

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Control de versiones para proyectos
    private int version = 1;
}
