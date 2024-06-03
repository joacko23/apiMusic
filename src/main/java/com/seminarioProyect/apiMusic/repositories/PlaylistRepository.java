package com.seminarioProyect.apiMusic.repositories;

import com.seminarioProyect.apiMusic.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
