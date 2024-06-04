package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.PlaylistRequest;
import com.seminarioProyect.apiMusic.dtos.PlaylistResponse;
import com.seminarioProyect.apiMusic.dtos.PlaylistListResponse;
import com.seminarioProyect.apiMusic.exceptions.ResourceNotFoundException;
import com.seminarioProyect.apiMusic.mappers.PlaylistMapper;
import com.seminarioProyect.apiMusic.models.Playlist;
import com.seminarioProyect.apiMusic.models.Tema;
import com.seminarioProyect.apiMusic.repositories.PlaylistRepository;
import com.seminarioProyect.apiMusic.repositories.TemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PlaylistServiceTest {
    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private TemaRepository temaRepository;

    @Mock
    private PlaylistMapper playlistMapper;

    @InjectMocks
    private PlaylistService playlistService;

    private PlaylistRequest playlistRequest;
    private Playlist playlist;
    private Tema tema1;
    private Tema tema2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        playlistRequest = new PlaylistRequest();
        playlistRequest.setNombre("My Playlist");
        playlistRequest.setCancionesIds(Arrays.asList(1L, 2L));

        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setNombre("My Playlist");

        tema1 = new Tema();
        tema1.setId(1L);

        tema2 = new Tema();
        tema2.setId(2L);

        when(playlistMapper.toEntity(playlistRequest)).thenReturn(playlist);
        when(playlistMapper.toResponse(playlist)).thenReturn(new PlaylistResponse());
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema1));
        when(temaRepository.findById(2L)).thenReturn(Optional.of(tema2));
    }

    @Test
    void crearPlaylist() {
        // Arrange
        when(playlistRepository.save(playlist)).thenReturn(playlist);

        // Act & Assert
        assertDoesNotThrow(() -> playlistService.crearPlaylist(playlistRequest));
        verify(playlistRepository, times(1)).save(playlist);
    }

    @Test
    void buscarPlaylistPorId() {
        // Arrange
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));

        // Act
        PlaylistResponse response = playlistService.buscarPlaylistPorId(1L);

        // Assert
        assertNotNull(response);
        verify(playlistRepository, times(1)).findById(1L);
    }

    @Test
    void agregarCancionAPlaylist() {
        // Arrange
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(playlistRepository.save(playlist)).thenReturn(playlist);

        // Act
        ResponseEntity<String> response = playlistService.agregarCancionAPlaylist(1L, 2L);

        // Assert
        assertNotNull(response);
        verify(playlistRepository, times(1)).save(playlist);
    }

    @Test
    void listarPlaylists() {
        // Arrange
        List<Playlist> playlists = Arrays.asList(new Playlist(), new Playlist());
        when(playlistRepository.findAll()).thenReturn(playlists);
        when(playlistMapper.toPlaylistListResponse(playlists)).thenReturn(new PlaylistListResponse());

        // Act
        PlaylistListResponse response = playlistService.listarPlaylists();

        // Assert
        assertNotNull(response);
        verify(playlistRepository, times(1)).findAll();
    }

    @Test
    void deletePlaylist() {
        // Arrange
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));

        // Act & Assert
        assertDoesNotThrow(() -> playlistService.deletePlaylist(1L));
        verify(playlistRepository, times(1)).delete(playlist);
    }

    @Test
    void actualizarPlaylist() {
        // Arrange
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));
        when(playlistRepository.save(playlist)).thenReturn(playlist);

        // Act & Assert
        assertDoesNotThrow(() -> playlistService.actualizarPlaylist(1L, playlistRequest));
        verify(playlistRepository, times(1)).save(playlist);
    }
}