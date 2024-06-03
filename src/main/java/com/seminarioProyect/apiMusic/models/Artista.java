package com.seminarioProyect.apiMusic.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipo; // Puede ser "Banda" o "Solista"

    @OneToMany(mappedBy = "artista")
    private List<Album> albumes;
}
