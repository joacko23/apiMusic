package com.seminarioProyect.apiMusic.controllers;

import com.seminarioProyect.apiMusic.dtos.AlbumRequest;
import com.seminarioProyect.apiMusic.dtos.AlbumResponse;
import com.seminarioProyect.apiMusic.dtos.AlbumesResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
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
    public ResponseEntity nuevoAlbum(@RequestBody AlbumRequest albumRequest) {
        return albumService.setAlbum(albumRequest);
    }

    @GetMapping(value = "/listarAlbums", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AlbumesResponse listarAlbums() {
        return albumService.listarAlbums();
    }

    @PutMapping(value = "/actualizarAlbum/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarAlbum(@PathVariable Long id, @RequestBody AlbumRequest albumRequest) {
        try {
            return albumService.updateAlbum(id, albumRequest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar el álbum: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/eliminarAlbum/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity eliminarAlbum(@PathVariable Long id) {
        try {
            return albumService.deleteAlbum(id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar el álbum: " + e.getMessage());
        }
    }


    @GetMapping(value = "/buscarAlbum/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> buscarAlbumPorId(@PathVariable Long id) {
        try {
            AlbumResponse albumResponse = albumService.buscarAlbumPorId(id);
            return ResponseEntity.ok(albumResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al buscar por id: " + e.getMessage());
        }
    }

    @PostMapping(value = "/agregarCancion/{albumId}/{cancionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumResponse> agregarCancionAAlbum(@PathVariable Long albumId, @PathVariable Long cancionId) {
        try {
            AlbumResponse albumResponse = albumService.agregarCancionAAlbum(albumId, cancionId);
            return ResponseEntity.ok(albumResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
