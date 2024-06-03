package com.seminarioProyect.apiMusic.mappers;

import com.seminarioProyect.apiMusic.dtos.PlaylistListResponse;
import com.seminarioProyect.apiMusic.dtos.PlaylistRequest;
import com.seminarioProyect.apiMusic.dtos.PlaylistResponse;
import com.seminarioProyect.apiMusic.dtos.TemaResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.models.Playlist;
import com.seminarioProyect.apiMusic.models.Tema;
import com.seminarioProyect.apiMusic.repositories.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaylistMapper {
    @Autowired
    private TemaRepository temaRepository;

    public Playlist toEntity(PlaylistRequest request) {
        Playlist playlist = new Playlist();
        playlist.setNombre(request.getNombre());
        if (request.getCancionesIds() != null) {
            List<Tema> canciones = request.getCancionesIds().stream()
                    .map(id -> temaRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + id)))
                    .collect(Collectors.toList());
            playlist.setCanciones(canciones);
        }
        return playlist;
    }

    public PlaylistResponse toResponse(Playlist playlist) {
        PlaylistResponse response = new PlaylistResponse();
        response.setId(playlist.getId());
        response.setNombre(playlist.getNombre());
        response.setCanciones(playlist.getCanciones().stream().map(cancion -> {
            TemaResponse temaResponse = new TemaResponse();
            temaResponse.setId(cancion.getId());
            temaResponse.setTitulo(cancion.getTitulo());
            return temaResponse;
        }).collect(Collectors.toList()));
        return response;
    }

    public PlaylistListResponse toPlaylistListResponse(List<Playlist> playlists) {
        List<PlaylistResponse> playlistResponses = playlists.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        PlaylistListResponse response = new PlaylistListResponse();
        response.setPlaylists(playlistResponses);
        return response;
    }
}
