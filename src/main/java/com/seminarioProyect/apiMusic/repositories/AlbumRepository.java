package com.seminarioProyect.apiMusic.repositories;

import com.seminarioProyect.apiMusic.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
