package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.AlbumRequest;
import com.seminarioProyect.apiMusic.dtos.AlbumResponse;
import com.seminarioProyect.apiMusic.dtos.AlbumesResponse;
import com.seminarioProyect.apiMusic.dtos.TemaRequest;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.mappers.AlbumMapper;
import com.seminarioProyect.apiMusic.models.Album;
import com.seminarioProyect.apiMusic.models.Artista;
import com.seminarioProyect.apiMusic.models.Tema;
import com.seminarioProyect.apiMusic.repositories.AlbumRepository;
import com.seminarioProyect.apiMusic.repositories.ArtistaRepository;
import com.seminarioProyect.apiMusic.repositories.TemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistaRepository artistaRepository;

    @Mock
    private TemaRepository temaRepository;

    @Mock
    private AlbumMapper albumMapper;

    @InjectMocks
    private AlbumService albumService;

    private AlbumRequest albumRequest;
    private Album album;
    private Artista artista;
    private Tema tema1;
    private Tema tema2;
    private TemaRequest temaRequest1;
    private TemaRequest temaRequest2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tema1 = new Tema();
        tema1.setId(1L);
        tema1.setTitulo("Tema 1");
        tema1.setDuracion("03:45");
        tema1.setLetra("Letra de tema 1");

        tema2 = new Tema();
        tema2.setId(2L);
        tema2.setTitulo("Tema 2");
        tema2.setDuracion("04:15");
        tema2.setLetra("Letra de tema 2");

        temaRequest1 = new TemaRequest();
        temaRequest1.setTitulo("Tema request 1");
        temaRequest1.setDuracion("03:45");
        temaRequest1.setLetra("Letra de tema 1");

        temaRequest2 = new TemaRequest();
        temaRequest2.setTitulo("Tema request 2");
        temaRequest2.setDuracion("04:15");
        temaRequest2.setLetra("Letra de tema 2");

        List<TemaRequest> temasRequest = Arrays.asList(temaRequest1, temaRequest2);

        albumRequest = new AlbumRequest();
        albumRequest.setNombre("Album request");
        albumRequest.setAnioLanzamiento(2022);
        albumRequest.setArtistaId(1L);
        albumRequest.setCanciones(temasRequest);

        artista = new Artista();
        artista.setId(1L);

        album = new Album();
        album.setId(1L);
        album.setNombre("Album clase");
        album.setAnioLanzamiento(2022);
        album.setArtista(artista);
        album.setCanciones(new ArrayList<>(Arrays.asList(tema1, tema2)));

        when(albumMapper.toEntity(albumRequest)).thenReturn(album);
        when(albumMapper.toResponse(album)).thenReturn(new AlbumResponse());
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artista));
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema1));
        when(temaRepository.findById(2L)).thenReturn(Optional.of(tema2));
    }

    @Test
    void setAlbum() {
        // Arrange
        when(albumRepository.save(album)).thenReturn(album);

        // Act & Assert
        assertDoesNotThrow(() -> albumService.setAlbum(albumRequest));
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    void listarAlbums() {
        // Arrange
        List<Album> albums = Arrays.asList(new Album(), new Album());
        when(albumRepository.findAll()).thenReturn(albums);
        when(albumMapper.albumListToResponse(albums)).thenReturn(new AlbumesResponse());

        // Act
        AlbumesResponse response = albumService.listarAlbums();

        // Assert
        assertNotNull(response);
        verify(albumRepository, times(1)).findAll();
    }

    @Test
    void buscarAlbumPorId() {
        // Arrange
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        // Act
        AlbumResponse response = albumService.buscarAlbumPorId(1L);

        // Assert
        assertNotNull(response);
        verify(albumRepository, times(1)).findById(1L);
    }

    @Test
    void agregarCancionAAlbum() {
        // Arrange
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(temaRepository.findById(2L)).thenReturn(Optional.of(tema2));
        when(albumRepository.save(any(Album.class))).thenReturn(album);
        when(albumMapper.toResponse(any(Album.class))).thenReturn(new AlbumResponse());

        // Act
        AlbumResponse response = albumService.agregarCancionAAlbum(1L, 2L);

        // Assert
        assertNotNull(response);
        verify(albumRepository, times(1)).findById(1L);
        verify(temaRepository, times(1)).findById(2L);
        verify(albumRepository, times(1)).save(album);
        verify(albumMapper, times(1)).toResponse(album);
    }

    @Test
    void deleteAlbum() {
        // Arrange
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        // Act & Assert
        assertDoesNotThrow(() -> albumService.deleteAlbum(1L));
        verify(albumRepository, times(1)).delete(album);
    }

    @Test
    void updateAlbum() {
        // Arrange
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(albumRepository.save(album)).thenReturn(album);

        // Act & Assert
        assertDoesNotThrow(() -> albumService.updateAlbum(1L, albumRequest));
        verify(albumRepository, times(1)).save(album);
    }
}