package com.seminarioProyect.apiMusic.mappers;


import com.seminarioProyect.apiMusic.dtos.*;
import com.seminarioProyect.apiMusic.models.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumMapper {
    @Autowired
    private TemaMapper temaMapper;

    public AlbumResponse toResponse(Album album) {
        AlbumResponse response = new AlbumResponse();
        response.setId(album.getId());
        response.setNombre(album.getNombre());
        response.setAnioLanzamiento(album.getAnioLanzamiento());
        response.setCanciones(album.getCanciones().stream()
                .map(temaMapper::toResponse)
                .collect(Collectors.toList()));
        response.setArtistaId(album.getArtista().getId());
        return response;
    }

    public Album toEntity(AlbumRequest albumRequest) {
        Album album = new Album();
        album.setNombre(albumRequest.getNombre());
        album.setAnioLanzamiento(albumRequest.getAnioLanzamiento());
        album.setCanciones(albumRequest.getCanciones().stream()
                .map(temaMapper::toEntity)
                .collect(Collectors.toList()));
        return album;
    }

    public AlbumesResponse albumListToResponse(List<Album> albumes) {
        List<AlbumResponse> albumResponseList = new ArrayList<>();

        for (Album album : albumes) {
            AlbumResponse albumResponse = new AlbumResponse();

            albumResponse.setId(album.getId());
            albumResponse.setNombre(album.getNombre());
            albumResponse.setAnioLanzamiento(album.getAnioLanzamiento());
            albumResponse.setCanciones(
                    album.getCanciones().stream()
                            .map(temaMapper::toResponse)
                            .collect(Collectors.toList())
            );
            albumResponseList.add(albumResponse);
        }
        AlbumesResponse albumesResponse = new AlbumesResponse();
        albumesResponse.setAlbumes(albumResponseList);
        return albumesResponse;
    }

}
