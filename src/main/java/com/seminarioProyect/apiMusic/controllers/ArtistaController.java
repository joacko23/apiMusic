package com.seminarioProyect.apiMusic.controllers;

import com.seminarioProyect.apiMusic.dtos.ArtistaRequest;
import com.seminarioProyect.apiMusic.dtos.ArtistaResponse;
import com.seminarioProyect.apiMusic.dtos.ArtistasResponse;
import com.seminarioProyect.apiMusic.services.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/artista")
@RestController
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;

    @PostMapping(value = "/nuevoArtista", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> nuevoArtista(@RequestBody ArtistaRequest artistaRequest) {
        return artistaService.setArtista(artistaRequest);
    }

    @GetMapping(value = "/listarArtistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArtistasResponse listarArtistas() {
        return artistaService.listarArtistas();
    }

    @GetMapping(value = "/buscarArtista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistaResponse> buscarArtistaPorId(@PathVariable Long id) {
        ArtistaResponse artistaResponse = artistaService.buscarArtistaPorId(id);
        return ResponseEntity.ok(artistaResponse);
    }

    @PutMapping(value = "/actualizarArtista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> actualizarArtista(@PathVariable Long id, @RequestBody ArtistaRequest artistaRequest) {
        return artistaService.actualizarArtista(id, artistaRequest);
    }

    @DeleteMapping(value = "/eliminarArtista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eliminarArtista(@PathVariable Long id) {
        return artistaService.eliminarArtista(id);
    }
}
