package com.seminarioProyect.apiMusic.repositories;

import com.seminarioProyect.apiMusic.models.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancionRepository extends JpaRepository<Cancion, Long> {
}
