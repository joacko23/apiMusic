package com.seminarioProyect.apiMusic.controllers;

import com.seminarioProyect.apiMusic.dtos.AlbumRequest;
import com.seminarioProyect.apiMusic.dtos.AlbumResponse;
import com.seminarioProyect.apiMusic.dtos.AlbumesResponse;
import com.seminarioProyect.apiMusic.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/album")
@Controller
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @PostMapping(value = "/nuevoAlbum", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> nuevoAlbum(@RequestBody AlbumRequest albumRequest) {
        return albumService.setAlbum(albumRequest);
    }

    @GetMapping(value = "/listarAlbums", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AlbumesResponse listarAlbums() {
        return albumService.listarAlbums();
    }

    @PutMapping(value = "/actualizarAlbum/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> actualizarAlbum(@PathVariable Long id, @RequestBody AlbumRequest albumRequest) {
        return albumService.updateAlbum(id, albumRequest);
    }

    @DeleteMapping(value = "/eliminarAlbum/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eliminarAlbum(@PathVariable Long id) {
        return albumService.deleteAlbum(id);
    }

    @GetMapping(value = "/buscarAlbum/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AlbumResponse> buscarAlbumPorId(@PathVariable Long id) {
        AlbumResponse albumResponse = albumService.buscarAlbumPorId(id);
        return ResponseEntity.ok(albumResponse);
    }

    @PostMapping(value = "/agregarCancion/{albumId}/{cancionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumResponse> agregarCancionAAlbum(@PathVariable Long albumId, @PathVariable Long cancionId) {
        AlbumResponse albumResponse = albumService.agregarCancionAAlbum(albumId, cancionId);
        return ResponseEntity.ok(albumResponse);
    }
}
