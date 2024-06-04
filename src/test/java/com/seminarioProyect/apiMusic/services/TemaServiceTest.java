package com.seminarioProyect.apiMusic.services;

import com.seminarioProyect.apiMusic.dtos.TemaRequest;
import com.seminarioProyect.apiMusic.dtos.TemaResponse;
import com.seminarioProyect.apiMusic.dtos.TemasResponse;
import com.seminarioProyect.apiMusic.mappers.TemaMapper;
import com.seminarioProyect.apiMusic.models.Tema;
import com.seminarioProyect.apiMusic.repositories.TemaRepository;
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

class TemaServiceTest {

    @Mock
    private TemaRepository temaRepository;

    @Mock
    private TemaMapper temaMapper;

    @InjectMocks
    private TemaService temaService;

    private TemaRequest temaRequest;
    private Tema tema;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        temaRequest = new TemaRequest();
        temaRequest.setTitulo("Tema una request");
        temaRequest.setDuracion("03:45");
        temaRequest.setLetra("Letra de tema");

        tema = new Tema();
        tema.setId(1L);
        tema.setTitulo("Tema uno clase");
        tema.setDuracion("03:45");
        tema.setLetra("Letra de tema");

        when(temaMapper.toEntity(temaRequest)).thenReturn(tema);
        when(temaMapper.toResponse(tema)).thenReturn(new TemaResponse());
    }

    @Test
    void setTema() {
        // Arrange
        when(temaRepository.save(tema)).thenReturn(tema);

        // Act & Assert
        assertDoesNotThrow(() -> temaService.setTema(temaRequest));
        verify(temaRepository, times(1)).save(tema);
    }

    @Test
    void listarTemas() {
        // Arrange
        List<Tema> temas = Arrays.asList(new Tema(), new Tema());
        when(temaRepository.findAll()).thenReturn(temas);
        when(temaMapper.TemasListToResponse(temas)).thenReturn(new TemasResponse());

        // Act
        TemasResponse response = temaService.listarTemas();

        // Assert
        assertNotNull(response);
        verify(temaRepository, times(1)).findAll();
    }

    @Test
    void buscarTemaPorId() {
        // Arrange
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));

        // Act
        TemaResponse response = temaService.buscarTemaPorId(1L);

        // Assert
        assertNotNull(response);
        verify(temaRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTema() {
        // Arrange
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));

        // Act & Assert
        assertDoesNotThrow(() -> temaService.deleteTema(1L));
        verify(temaRepository, times(1)).delete(tema);
    }

    @Test
    void updateTema() {
        // Arrange
        when(temaRepository.findById(1L)).thenReturn(Optional.of(tema));
        when(temaRepository.save(tema)).thenReturn(tema);

        // Act & Assert
        assertDoesNotThrow(() -> temaService.updateTema(1L, temaRequest));
        verify(temaRepository, times(1)).save(tema);
    }
}