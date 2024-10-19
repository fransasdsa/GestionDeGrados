// src/main/java/com/universidad/project/model/DocumentMetadata.java
package com.universidad.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentMetadata {
    private String documentId;
    private String nombreArchivo;
    private String tipoArchivo;
    private String url;
    private LocalDateTime fechaSubida;
    private String subidoPor; // ID del usuario que subió el documento
}
