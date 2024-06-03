package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.ArtistaRequest;
import com.seminarioProyect.apiMusic.dtos.ArtistaResponse;
import com.seminarioProyect.apiMusic.dtos.ArtistasResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.mappers.ArtistaMapper;
import com.seminarioProyect.apiMusic.models.Artista;
import com.seminarioProyect.apiMusic.repositories.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArtistaService {
    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private ArtistaMapper artistaMapper;

    public ResponseEntity<String> setArtista(ArtistaRequest artistaRequest) {
        Artista artista = artistaMapper.toEntity(artistaRequest);
        artistaRepository.save(artista);
        return ResponseEntity.ok("Artista guardado: " + artista.getNombre());
    }

    public ArtistasResponse listarArtistas() {
        List<Artista> artistas = artistaRepository.findAll();
        return artistaMapper.artistaListToResponse(artistas);
    }

    public ArtistaResponse buscarArtistaPorId(Long id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + id));
        return artistaMapper.toResponse(artista);
    }

    public ResponseEntity<String> actualizarArtista(Long id, ArtistaRequest artistaRequest) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + id));

        artista.setNombre(artistaRequest.getNombre());
        artista.setTipo(artistaRequest.getTipo());

        artistaRepository.save(artista);
        return ResponseEntity.ok("Artista actualizado: " + artista.getNombre());
    }

    public ResponseEntity<String> eliminarArtista(Long id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + id));
        artistaRepository.delete(artista);
        return ResponseEntity.ok("Artista eliminado: " + artista.getNombre());
    }
}
