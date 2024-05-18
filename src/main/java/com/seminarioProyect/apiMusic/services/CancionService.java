package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.CancionRequest;
import com.seminarioProyect.apiMusic.dtos.CancionResponse;
import com.seminarioProyect.apiMusic.dtos.CancionesResponse;
import com.seminarioProyect.apiMusic.mappers.CancionMapper;
import com.seminarioProyect.apiMusic.models.Cancion;
import com.seminarioProyect.apiMusic.repositories.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CancionService {
    @Autowired
    public CancionRepository cancionRepository;
    @Autowired
    public CancionMapper cancionMapper;

    public ResponseEntity setCancion(CancionRequest cancionRequest){
        if (cancionRequest == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La solicitud de canción no puede ser nula");
        }
        Cancion cancion = cancionMapper.toEntity(cancionRequest);
        cancionRepository.save(cancion);
        return ResponseEntity.ok("Cancion guardada: " + cancion.getTitulo());
    }

    public CancionesResponse listarCanciones(){
        try {
            List<Cancion> canciones = cancionRepository.findAll();
            return cancionMapper.cancionListToResponse(canciones);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al recuperar la lista de canciones", e);
        }
    }

    public ResponseEntity updateCancion(Long id, CancionRequest cancionRequest) {
        Optional<Cancion> optionalCancion = cancionRepository.findById(id);
        if (optionalCancion.isPresent()) {
            Cancion cancion = optionalCancion.get();
            cancion.setTitulo(cancionRequest.getTitulo());
            cancion.setDuracion(cancionRequest.getDuracion());
            cancion.setLetra(cancionRequest.getLetra());
            cancionRepository.save(cancion);
            return ResponseEntity.ok("Cancion actualizada: " + cancion.getTitulo());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Canción no encontrada");
        }
    }

    public ResponseEntity deleteCancion(Long id) {
        Optional<Cancion> optionalCancion = cancionRepository.findById(id);
        if (optionalCancion.isPresent()) {
            Cancion cancion = optionalCancion.get();
            cancionRepository.delete(cancion);
            return ResponseEntity.ok("Cancion eliminada: " + cancion.getTitulo());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Canción no encontrada");
        }
    }

    public CancionResponse buscarCancionPorId(Long id) {
        try {
            Optional<Cancion> optionalCancion = cancionRepository.findById(id);
            Cancion cancion = optionalCancion.orElseThrow(() -> new NoSuchElementException("Canción no encontrada"));
            return cancionMapper.toResponse(cancion);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la canción por ID", e);
        }
    }
}
