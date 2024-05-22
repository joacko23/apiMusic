package com.seminarioProyect.apiMusic.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AlbumesResponse {
    List<AlbumResponse> albumes;
}
