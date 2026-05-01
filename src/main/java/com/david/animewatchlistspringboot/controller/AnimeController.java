package com.david.animewatchlistspringboot.controller;


import com.david.animewatchlistspringboot.DTO.AnimeDTO;
import com.david.animewatchlistspringboot.entity.Anime;
import com.david.animewatchlistspringboot.entity.User;
import com.david.animewatchlistspringboot.repository.AnimeRepository;
import com.david.animewatchlistspringboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/list/{uuid}")
    public List<AnimeDTO> findUserbyId(@PathVariable UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return animeRepository.findByUserUuid(uuid).stream()
                .map(anime -> {
                    AnimeDTO animeDTO = new AnimeDTO();
                    animeDTO.setUserId(user.getUuid());
                    animeDTO.setId(anime.getId());
                    animeDTO.setTitle(anime.getTitle());
                    animeDTO.setStatus(anime.getStatus());
                    animeDTO.setProgress(anime.getProgress());
                    animeDTO.setGenres(anime.getGenres());
                    animeDTO.setRating(anime.getRating());
                    animeDTO.setIsFavourite(anime.isFavourite());
                    return animeDTO;
                }).toList();
    }

    @PostMapping("/add/{uuid}")
    public ResponseEntity<Anime> addAnime(@RequestBody AnimeDTO animeDTO, @PathVariable String uuid) {
        User user = userRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("User not found"));
        Anime newAnime = new Anime();
        newAnime.setId(animeDTO.getId());
        newAnime.setTitle(animeDTO.getTitle());
        newAnime.setStatus(animeDTO.getStatus());
        newAnime.setProgress(animeDTO.getProgress());
        newAnime.setGenres(animeDTO.getGenres());
        newAnime.setRating(animeDTO.getRating());
        newAnime.setFavourite(animeDTO.getIsFavourite());
        newAnime.setUser(user);
        Anime savedAnime = animeRepository.save(newAnime);
        return ResponseEntity.ok(savedAnime);
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable Long id, @RequestBody Anime animeDetails) {
        Anime anime = animeRepository.findById(id).orElseThrow(() -> new RuntimeException("Anime not found"));
        anime.setTitle(animeDetails.getTitle());
        anime.setStatus(animeDetails.getStatus());
        anime.setProgress(animeDetails.getProgress());
        anime.setGenres(animeDetails.getGenres());
        anime.setRating(animeDetails.getRating());
        anime.setFavourite(animeDetails.isFavourite());
        Anime updatedAnime = animeRepository.existsById(id) ? animeRepository.save(anime) : null;
        return ResponseEntity.ok(updatedAnime);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAnime(@PathVariable Long id) {
        return animeRepository.findById(id).map(anime -> {
            animeRepository.delete(anime);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
