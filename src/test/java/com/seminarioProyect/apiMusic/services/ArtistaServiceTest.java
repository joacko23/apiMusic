package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.ArtistaRequest;
import com.seminarioProyect.apiMusic.dtos.ArtistaResponse;
import com.seminarioProyect.apiMusic.dtos.ArtistasResponse;
import com.seminarioProyect.apiMusic.mappers.ArtistaMapper;
import com.seminarioProyect.apiMusic.models.Artista;
import com.seminarioProyect.apiMusic.repositories.ArtistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArtistaServiceTest {

    @Mock
    private ArtistaRepository artistaRepository;

    @Mock
    private ArtistaMapper artistaMapper;

    @InjectMocks
    private ArtistaService artistaService;

    private ArtistaRequest artistaRequest;
    private Artista artista;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        artistaRequest = new ArtistaRequest();
        artistaRequest.setNombre("Red Hot request");
        artistaRequest.setTipo("Banda");

        artista = new Artista();
        artista.setId(1L);
        artista.setNombre("Red Hot clase");
        artista.setTipo("Banda");

        when(artistaMapper.toEntity(artistaRequest)).thenReturn(artista);
        when(artistaMapper.toResponse(artista)).thenReturn(new ArtistaResponse());
    }

    @Test
    void setArtista() {
        // Arrange
        when(artistaRepository.save(artista)).thenReturn(artista);

        // Act & Assert
        assertDoesNotThrow(() -> artistaService.setArtista(artistaRequest));
        verify(artistaRepository, times(1)).save(artista);
    }

    @Test
    void listarArtistas() {
        // Arrange
        List<Artista> artistas = Arrays.asList(new Artista(), new Artista());
        when(artistaRepository.findAll()).thenReturn(artistas);
        when(artistaMapper.artistaListToResponse(artistas)).thenReturn(new ArtistasResponse());

        // Act
        ArtistasResponse response = artistaService.listarArtistas();

        // Assert
        assertNotNull(response);
        verify(artistaRepository, times(1)).findAll();
    }

    @Test
    void buscarArtistaPorId() {
        // Arrange
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artista));

        // Act
        ArtistaResponse response = artistaService.buscarArtistaPorId(1L);

        // Assert
        assertNotNull(response);
        verify(artistaRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarArtista() {
        // Arrange
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artista));

        // Act & Assert
        assertDoesNotThrow(() -> artistaService.eliminarArtista(1L));
        verify(artistaRepository, times(1)).delete(artista);
    }

    @Test
    void actualizarArtista() {
        // Arrange
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artista));
        when(artistaRepository.save(artista)).thenReturn(artista);

        // Act & Assert
        assertDoesNotThrow(() -> artistaService.actualizarArtista(1L, artistaRequest));
        verify(artistaRepository, times(1)).save(artista);
    }
}