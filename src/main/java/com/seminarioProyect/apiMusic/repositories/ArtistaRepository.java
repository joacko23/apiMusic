package com.seminarioProyect.apiMusic.repositories;

import com.seminarioProyect.apiMusic.models.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}
