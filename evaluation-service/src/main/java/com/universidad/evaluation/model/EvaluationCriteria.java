// src/main/java/com/universidad/evaluation/model/EvaluationCriteria.java
package com.universidad.evaluation.model;

import lombok.Data;

@Data
public class EvaluationCriteria {
    private String criterio;
    private String descripcion;
    private Integer puntuacionMaxima;
}
