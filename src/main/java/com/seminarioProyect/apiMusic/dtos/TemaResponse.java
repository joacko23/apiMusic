package com.seminarioProyect.apiMusic.dtos;

import lombok.Data;

@Data
public class TemaResponse {
    private Long id;
    private String titulo;
    private String duracion;
    private String letra;
}
