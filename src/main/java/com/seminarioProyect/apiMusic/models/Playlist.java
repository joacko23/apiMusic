package com.seminarioProyect.apiMusic.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "playlist_canciones",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "cancion_id")
    )
    private List<Tema> canciones = new ArrayList<>();

    public void agregarCancion(Tema tema) {
        this.canciones.add(tema);
    }
}
