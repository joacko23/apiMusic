package com.seminarioProyect.apiMusic.controllers;

import com.seminarioProyect.apiMusic.dtos.PlaylistListResponse;
import com.seminarioProyect.apiMusic.dtos.PlaylistRequest;
import com.seminarioProyect.apiMusic.dtos.PlaylistResponse;
import com.seminarioProyect.apiMusic.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/playlist")
@RestController
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping(value = "/nuevaPlaylist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> nuevaPlaylist(@RequestBody PlaylistRequest request) {
        return playlistService.crearPlaylist(request);
    }

    @GetMapping(value = "/buscarPlaylist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaylistResponse> buscarPlaylistPorId(@PathVariable Long id) {
        PlaylistResponse response = playlistService.buscarPlaylistPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/agregarCancion/{playlistId}/{cancionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> agregarCancionAPlaylist(@PathVariable Long playlistId, @PathVariable Long cancionId) {
        return playlistService.agregarCancionAPlaylist(playlistId, cancionId);
    }

    @GetMapping(value = "/listarPlaylists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaylistListResponse> listarPlaylists() {
        PlaylistListResponse response = playlistService.listarPlaylists();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/eliminarPlaylist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eliminarPlaylist(@PathVariable Long id) {
        return playlistService.deletePlaylist(id);
    }

    @PutMapping(value = "/actualizarPlaylist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> actualizarPlaylist(@PathVariable Long id, @RequestBody PlaylistRequest request) {
        return playlistService.actualizarPlaylist(id, request);
    }
}
