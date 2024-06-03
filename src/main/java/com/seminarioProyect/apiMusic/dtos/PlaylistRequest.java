package com.seminarioProyect.apiMusic.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistRequest {
    private String nombre;
    private List<Long> cancionesIds;
}
