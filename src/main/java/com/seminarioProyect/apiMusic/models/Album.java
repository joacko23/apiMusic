package com.seminarioProyect.apiMusic.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "album")
@Data
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int anioLanzamiento;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private List<Tema> canciones;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    public void agregarCancion(Tema tema) {
        this.canciones.add(tema);
    }

}
