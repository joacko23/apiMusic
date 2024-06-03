package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.PlaylistListResponse;
import com.seminarioProyect.apiMusic.dtos.PlaylistRequest;
import com.seminarioProyect.apiMusic.dtos.PlaylistResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.mappers.PlaylistMapper;
import com.seminarioProyect.apiMusic.models.Album;
import com.seminarioProyect.apiMusic.models.Playlist;
import com.seminarioProyect.apiMusic.models.Tema;
import com.seminarioProyect.apiMusic.repositories.PlaylistRepository;
import com.seminarioProyect.apiMusic.repositories.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private TemaRepository temaRepository;

    public ResponseEntity<String> crearPlaylist(PlaylistRequest request) {
        Playlist playlist = playlistMapper.toEntity(request);
        playlistRepository.save(playlist);
        return ResponseEntity.ok("Playlist creada: " + playlist.getNombre());
    }

    public PlaylistResponse buscarPlaylistPorId(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist no encontrada con id: " + id));
        return playlistMapper.toResponse(playlist);
    }

    public ResponseEntity<String> agregarCancionAPlaylist(Long playlistId, Long cancionId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist no encontrada con id: " + playlistId));
        Tema cancion = temaRepository.findById(cancionId)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + cancionId));

        playlist.agregarCancion(cancion);
        playlistRepository.save(playlist);

        return ResponseEntity.ok("Canción agregada a la playlist: " + playlist.getNombre());
    }

    public PlaylistListResponse listarPlaylists() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlistMapper.toPlaylistListResponse(playlists);
    }

    public ResponseEntity<String> deletePlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist no encontrada con id: " + id));
        playlistRepository.delete(playlist);
        return ResponseEntity.ok("Playlist eliminada: " + playlist.getNombre());
    }

    public ResponseEntity<String> actualizarPlaylist(Long id, PlaylistRequest playlistRequest) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist no encontrada con id: " + id));

        playlist.setNombre(playlistRequest.getNombre());
        List<Tema> canciones = playlistRequest.getCancionesIds().stream()
                .map(cancionId -> temaRepository.findById(cancionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + cancionId)))
                .collect(Collectors.toList());
        playlist.setCanciones(canciones);

        playlistRepository.save(playlist);
        return ResponseEntity.ok("Playlist actualizada: " + playlist.getNombre());
    }
}
