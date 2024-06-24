package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.AlbumRequest;
import com.seminarioProyect.apiMusic.dtos.AlbumResponse;
import com.seminarioProyect.apiMusic.dtos.AlbumesResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.mappers.AlbumMapper;
import com.seminarioProyect.apiMusic.models.Album;
import com.seminarioProyect.apiMusic.models.Artista;
import com.seminarioProyect.apiMusic.models.Tema;
import com.seminarioProyect.apiMusic.repositories.AlbumRepository;
import com.seminarioProyect.apiMusic.repositories.ArtistaRepository;
import com.seminarioProyect.apiMusic.repositories.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private ArtistaRepository artistaRepository;

    public ResponseEntity<String> setAlbum(AlbumRequest albumRequest) {
        Artista artista = artistaRepository.findById(albumRequest.getArtistaId())
                .orElseThrow(() -> new ResourceNotFoundException("Artista no encontrado con id: " + albumRequest.getArtistaId()));
        Album album = albumMapper.toEntity(albumRequest);
        album.setArtista(artista);
        albumRepository.save(album);
        return ResponseEntity.ok("Álbum guardado: " + album.getNombre());
    }

    public AlbumesResponse listarAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albumMapper.albumListToResponse(albums);
    }

    public ResponseEntity<String> updateAlbum(Long id, AlbumRequest albumRequest) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado con id: " + id));

        album.setNombre(albumRequest.getNombre());
        album.setAnioLanzamiento(albumRequest.getAnioLanzamiento());
        album.setCanciones(albumMapper.toEntity(albumRequest).getCanciones());

        albumRepository.save(album);
        return ResponseEntity.ok("Álbum actualizado: " + album.getNombre());
    }

    public ResponseEntity<String> deleteAlbum(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado con id: " + id));
        albumRepository.delete(album);
        return ResponseEntity.ok("Álbum eliminado: " + album.getNombre());
    }

    public AlbumResponse buscarAlbumPorId(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado con id: " + id));
        return albumMapper.toResponse(album);
    }

    public AlbumResponse agregarCancionAAlbum(Long albumId, Long cancionId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album no encontrado con id: " + albumId));
        Tema tema = temaRepository.findById(cancionId)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada con id: " + cancionId));

        if (album.getCanciones() == null) {
            album.setCanciones(new ArrayList<>());
        }
        album.getCanciones().add(tema);
        albumRepository.save(album);

        return albumMapper.toResponse(album);
    }
}
