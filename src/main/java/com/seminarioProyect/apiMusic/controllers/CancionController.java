package com.seminarioProyect.apiMusic.controllers;

import com.seminarioProyect.apiMusic.dtos.CancionRequest;
import com.seminarioProyect.apiMusic.dtos.CancionResponse;
import com.seminarioProyect.apiMusic.dtos.CancionesResponse;
import com.seminarioProyect.apiMusic.services.CancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/cancion")
@Controller
public class CancionController {
    @Autowired
    private CancionService cancionService;

    @PostMapping(value = "/nuevaCancion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevaCancion(@RequestBody CancionRequest cancionRequest){
        try {
            return cancionService.setCancion(cancionRequest);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping(value = "/listarCanciones", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity listarCanciones(){
        try {
            return ResponseEntity.ok(cancionService.listarCanciones());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping(value = "/actualizarCancion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarCancion(@PathVariable Long id, @RequestBody CancionRequest cancionRequest){
        try {
            return cancionService.updateCancion(id, cancionRequest);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping(value = "/eliminarCancion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity eliminarCancion(@PathVariable Long id){
        try {
            return cancionService.deleteCancion(id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping(value = "/buscarCancion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CancionResponse buscarCancionPorId(@PathVariable Long id){
        try {
            return cancionService.buscarCancionPorId(id);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la canción por ID", e);
        }
    }
}

