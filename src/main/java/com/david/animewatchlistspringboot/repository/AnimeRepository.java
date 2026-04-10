package com.david.animewatchlistspringboot.repository;

import com.david.animewatchlistspringboot.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    Anime findByTitle(String title);
    List<Anime> findUserById(Long UserId);

}
