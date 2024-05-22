package com.seminarioProyect.apiMusic.controllers;

import com.seminarioProyect.apiMusic.dtos.CancionRequest;
import com.seminarioProyect.apiMusic.dtos.CancionResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.services.CancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cancion")
@Controller
public class CancionController {
    @Autowired
    private CancionService cancionService;

    @PostMapping(value = "/nuevaCancion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevaCancion(@RequestBody CancionRequest cancionRequest) {
        try {
            return cancionService.setCancion(cancionRequest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar la canción: " + e.getMessage());
        }
    }

    @GetMapping(value = "/listarCanciones", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity listarCanciones() {
        try {
            return ResponseEntity.ok(cancionService.listarCanciones());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al listar las canciones: " + e.getMessage());
        }
    }

    @PutMapping(value = "/actualizarCancion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarCancion(@PathVariable Long id, @RequestBody CancionRequest cancionRequest) {
        try {
            return cancionService.updateCancion(id, cancionRequest);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar la canción: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/eliminarCancion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity eliminarCancion(@PathVariable Long id) {
        try {
            return cancionService.deleteCancion(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar la canción: " + e.getMessage());
        }
    }

    @GetMapping(value = "/buscarCancion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> buscarCancionPorId(@PathVariable Long id){
        try {
            CancionResponse cancionResponse = cancionService.buscarCancionPorId(id);
            return ResponseEntity.ok(cancionResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al buscar por id: " + e.getMessage());
        }
    }
}

