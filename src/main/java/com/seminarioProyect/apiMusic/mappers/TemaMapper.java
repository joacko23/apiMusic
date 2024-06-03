package com.seminarioProyect.apiMusic.mappers;

import com.seminarioProyect.apiMusic.dtos.TemaRequest;
import com.seminarioProyect.apiMusic.dtos.TemaResponse;
import com.seminarioProyect.apiMusic.dtos.TemasResponse;
import com.seminarioProyect.apiMusic.models.Tema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TemaMapper {
    public TemaResponse toResponse(Tema tema) {
        TemaResponse response = new TemaResponse();

        response.setId(tema.getId());
        response.setTitulo(tema.getTitulo());
        response.setDuracion(tema.getDuracion());
        response.setLetra(tema.getLetra());
        return response;
    }

    public Tema toEntity(TemaRequest temaRequest) {
        Tema tema = new Tema();
        tema.setTitulo(temaRequest.getTitulo());
        tema.setDuracion(temaRequest.getDuracion());
        tema.setLetra(temaRequest.getLetra());

        return tema;
    }

    public TemasResponse TemasListToResponse(List<Tema> canciones) {
        List<TemaResponse> temaResponseList = new ArrayList<>();

        for (Tema tema : canciones) {
            TemaResponse temaResponse = new TemaResponse();

            temaResponse.setId(tema.getId());
            temaResponse.setTitulo(tema.getTitulo());
            temaResponse.setDuracion(tema.getDuracion());
            temaResponse.setLetra(tema.getLetra());

            temaResponseList.add(temaResponse);
        }
        TemasResponse temasResponse = new TemasResponse();
        temasResponse.setCanciones(temaResponseList);
        return temasResponse;
    }
}