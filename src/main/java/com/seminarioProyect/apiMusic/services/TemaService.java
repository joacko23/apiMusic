package com.seminarioProyect.apiMusic.services;
import com.seminarioProyect.apiMusic.dtos.TemaRequest;
import com.seminarioProyect.apiMusic.dtos.TemaResponse;
import com.seminarioProyect.apiMusic.dtos.TemasResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.mappers.TemaMapper;
import com.seminarioProyect.apiMusic.models.Tema;
import com.seminarioProyect.apiMusic.repositories.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemaService {
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private TemaMapper temaMapper;

    public ResponseEntity<String> setTema(TemaRequest temaRequest){
        Tema tema = temaMapper.toEntity(temaRequest);
        temaRepository.save(tema);
        return ResponseEntity.ok("Cancion guardada: " + tema.getTitulo());
    }

    public TemasResponse listarTemas(){
        List<Tema> canciones = temaRepository.findAll();
        return temaMapper.TemasListToResponse(canciones);
    }

    public ResponseEntity<String> updateTema(Long id, TemaRequest temaRequest){
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada con id: " + id));

        tema.setTitulo(temaRequest.getTitulo());
        tema.setDuracion(temaRequest.getDuracion());
        tema.setLetra(temaRequest.getLetra());

        temaRepository.save(tema);
        return ResponseEntity.ok("Cancion actualizada: " + tema.getTitulo());
    }

    public ResponseEntity<String> deleteTema(Long id){
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada con id: " + id));
        temaRepository.delete(tema);
        return ResponseEntity.ok("Cancion eliminada: " + tema.getTitulo());
    }

    public TemaResponse buscarTemaPorId(Long id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canción no encontrada con id: " + id));
        return temaMapper.toResponse(tema);
    }
}

