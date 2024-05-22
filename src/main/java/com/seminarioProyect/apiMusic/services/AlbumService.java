package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.AlbumRequest;
import com.seminarioProyect.apiMusic.dtos.AlbumResponse;
import com.seminarioProyect.apiMusic.dtos.AlbumesResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.mappers.AlbumMapper;
import com.seminarioProyect.apiMusic.models.Album;
import com.seminarioProyect.apiMusic.models.Cancion;
import com.seminarioProyect.apiMusic.repositories.AlbumRepository;
import com.seminarioProyect.apiMusic.repositories.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private CancionRepository cancionRepository;

    public ResponseEntity setAlbum(AlbumRequest albumRequest) {
        try {
            Album album = albumMapper.toEntity(albumRequest);
            albumRepository.save(album);
            return ResponseEntity.ok("Álbum guardado: " + album.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el álbum: " + e.getMessage(), e);
        }
    }

    public AlbumesResponse listarAlbums() {
        try {
            List<Album> albums = albumRepository.findAll();
            return albumMapper.albumListToResponse(albums);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los álbumes: " + e.getMessage(), e);
        }
    }

    public ResponseEntity updateAlbum(Long id, AlbumRequest albumRequest) {
        try {
            Album album = albumRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado con id: " + id));

            album.setNombre(albumRequest.getNombre());
            album.setAnioLanzamiento(albumRequest.getAnioLanzamiento());
            album.setCanciones(albumMapper.toEntity(albumRequest).getCanciones());

            albumRepository.save(album);
            return ResponseEntity.ok("Álbum actualizado: " + album.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el álbum: " + e.getMessage(), e);
        }
    }

    public ResponseEntity deleteAlbum(Long id) {
        try {
            Album album = albumRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado con id: " + id));
            albumRepository.delete(album);
            return ResponseEntity.ok("Álbum eliminado: " + album.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el álbum: " + e.getMessage(), e);
        }
    }

    public AlbumResponse buscarAlbumPorId(Long id) {
        try {
            Album album = albumRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Álbum no encontrado con id: " + id));
            return albumMapper.toResponse(album);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el álbum: " + e.getMessage(), e);
        }
    }


    public AlbumResponse agregarCancionAAlbum(Long albumId, Long cancionId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album no encontrado con id: " + albumId));
        Cancion cancion = cancionRepository.findById(cancionId)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion no encontrada con id: " + cancionId));

        album.agregarCancion(cancion);
        albumRepository.save(album);

        return albumMapper.toResponse(album);
    }
}
