package com.seminarioProyect.apiMusic.mappers;

import com.seminarioProyect.apiMusic.dtos.*;
import com.seminarioProyect.apiMusic.models.Artista;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArtistaMapper {
    public Artista toEntity(ArtistaRequest artistaRequest) {
        Artista artista = new Artista();
        artista.setNombre(artistaRequest.getNombre());
        artista.setTipo(artistaRequest.getTipo());
        return artista;
    }

    public ArtistaResponse toResponse(Artista artista) {
        ArtistaResponse response = new ArtistaResponse();
        response.setId(artista.getId());
        response.setNombre(artista.getNombre());
        response.setTipo(artista.getTipo());
        return response;
    }

    public ArtistasResponse artistaListToResponse(List<Artista> artistas) {
        List<ArtistaResponse> artistaResponseList = new ArrayList<>();

        for (Artista artista : artistas) {
            ArtistaResponse artistaResponse = new ArtistaResponse();

            artistaResponse.setId(artista.getId());
            artistaResponse.setNombre(artista.getNombre());
            artistaResponse.setTipo(artista.getTipo());


            artistaResponseList.add(artistaResponse);
        }
        ArtistasResponse artistasResponse = new ArtistasResponse();
        artistasResponse.setArtistas(artistaResponseList);
        return artistasResponse;
    }
}
