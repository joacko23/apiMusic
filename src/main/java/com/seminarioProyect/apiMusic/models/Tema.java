package com.seminarioProyect.apiMusic.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tema")
@Data
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "duracion")
    private String duracion;
    @Column(name = "letra")
    private String letra;
}
