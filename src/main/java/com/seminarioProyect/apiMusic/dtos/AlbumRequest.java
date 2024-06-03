package com.seminarioProyect.apiMusic.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AlbumRequest {
    private String nombre;
    private int anioLanzamiento;
    private List<TemaRequest> canciones;
    private Long artistaId;
}
