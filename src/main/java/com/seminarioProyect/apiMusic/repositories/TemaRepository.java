package com.seminarioProyect.apiMusic.repositories;

import com.seminarioProyect.apiMusic.models.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepository extends JpaRepository<Tema, Long> {
}
