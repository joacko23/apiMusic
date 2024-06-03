package com.seminarioProyect.apiMusic.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistResponse {
    private Long id;
    private String nombre;
    private List<TemaResponse> canciones;
}
