// src/main/java/com/universidad/project/model/Comment.java
package com.universidad.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private String autorId;
    private String mensaje;
    private LocalDateTime fecha;
}
