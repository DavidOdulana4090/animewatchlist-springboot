package com.david.animewatchlistspringboot.repository;

import com.david.animewatchlistspringboot.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findByUserUuid(UUID uuid);

}
