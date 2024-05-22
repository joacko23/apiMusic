package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.CancionRequest;
import com.seminarioProyect.apiMusic.dtos.CancionResponse;
import com.seminarioProyect.apiMusic.dtos.CancionesResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.mappers.CancionMapper;
import com.seminarioProyect.apiMusic.models.Cancion;
import com.seminarioProyect.apiMusic.repositories.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancionService {
    @Autowired
    public CancionRepository cancionRepository;
    @Autowired
    public CancionMapper cancionMapper;

    public ResponseEntity setCancion(CancionRequest cancionRequest){
        try {
            Cancion cancion = cancionMapper.toEntity(cancionRequest);
            cancionRepository.save(cancion);
            return ResponseEntity.ok("Cancion guardada: " + cancion.getTitulo());
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la canción: " + e.getMessage(), e);
        }
    }

    public CancionesResponse listarCanciones(){
        try {
            List<Cancion> canciones = cancionRepository.findAll();
            return cancionMapper.cancionListToResponse(canciones);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las canciones: " + e.getMessage(), e);
        }
    }

    public ResponseEntity updateCancion(Long id, CancionRequest cancionRequest){
        try {
            Cancion cancion = cancionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada con id: " + id));

            cancion.setTitulo(cancionRequest.getTitulo());
            cancion.setDuracion(cancionRequest.getDuracion());
            cancion.setLetra(cancionRequest.getLetra());

            cancionRepository.save(cancion);
            return ResponseEntity.ok("Cancion actualizada: " + cancion.getTitulo());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la canción: " + e.getMessage(), e);
        }
    }

    public ResponseEntity deleteCancion(Long id){
        try {
            Cancion cancion = cancionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada con id: " + id));
            cancionRepository.delete(cancion);
            return ResponseEntity.ok("Cancion eliminada: " + cancion.getTitulo());
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la canción: " + e.getMessage(), e);
        }
    }

    public CancionResponse buscarCancionPorId(Long id) {
        try {
            Cancion cancion = cancionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + id));
            return cancionMapper.toResponse(cancion);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar la canción: " + e.getMessage(), e);
        }
    }
}
