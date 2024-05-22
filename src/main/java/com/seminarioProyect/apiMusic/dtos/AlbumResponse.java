package com.seminarioProyect.apiMusic.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AlbumResponse {
    private Long id;
    private String nombre;
    private int anioLanzamiento;
    private List<CancionResponse> canciones;
}
