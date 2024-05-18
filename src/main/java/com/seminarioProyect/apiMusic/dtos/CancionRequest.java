package com.seminarioProyect.apiMusic.dtos;

import lombok.Data;

@Data
public class CancionRequest {
    private String titulo;
    private String duracion;
    private String letra;
}
