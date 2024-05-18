package com.seminarioProyect.apiMusic.mappers;

import com.seminarioProyect.apiMusic.dtos.CancionRequest;
import com.seminarioProyect.apiMusic.dtos.CancionResponse;
import com.seminarioProyect.apiMusic.dtos.CancionesResponse;
import com.seminarioProyect.apiMusic.models.Cancion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CancionMapper {
    public CancionResponse toResponse(Cancion cancion) {
        CancionResponse response = new CancionResponse();

        response.setId(cancion.getId());
        response.setTitulo(cancion.getTitulo());
        response.setDuracion(cancion.getDuracion());
        response.setLetra(cancion.getLetra());
        return response;
    }

    public Cancion toEntity(CancionRequest cancionRequest) {
        Cancion cancion = new Cancion();
        cancion.setTitulo(cancionRequest.getTitulo());
        cancion.setDuracion(cancionRequest.getDuracion());
        cancion.setLetra(cancionRequest.getLetra());

        return cancion;
    }

    public CancionesResponse cancionListToResponse(List<Cancion> canciones) {
        List<CancionResponse> cancionResponseList = new ArrayList<>();

        for (Cancion cancion : canciones) {
            CancionResponse cancionResponse = new CancionResponse();

            cancionResponse.setId(cancion.getId());
            cancionResponse.setTitulo(cancion.getTitulo());
            cancionResponse.setDuracion(cancion.getDuracion());
            cancionResponse.setLetra(cancion.getLetra());

            cancionResponseList.add(cancionResponse);
        }
        CancionesResponse cancionesResponse = new CancionesResponse();
        cancionesResponse.setCanciones(cancionResponseList);
        return cancionesResponse;
    }
}