package com.david.animewatchlistspringboot.controller;


import com.david.animewatchlistspringboot.DTO.AnimeDTO;
import com.david.animewatchlistspringboot.entity.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private com.david.animewatchlistspringboot.repository.AnimeRepository animeRepository;

    @GetMapping("/all/{Id}")
    public List<AnimeDTO> findUserbyId(@PathVariable Long Id) {
        return animeRepository.findByUserId(Id).stream()
                .map(anime -> {
                    AnimeDTO animeDTO = new AnimeDTO();
                    animeDTO.setId(anime.getId());
                    animeDTO.setTitle(anime.getTitle());
                    animeDTO.setStatus(anime.getStatus());
                    animeDTO.setProgress(anime.getProgress());
                    animeDTO.setGenre(anime.getGenre());
                    animeDTO.setRating(anime.getRating());
                    animeDTO.setFavourites(anime.isFavourites());
                    return animeDTO;
                }).toList();
    }

    @PostMapping("/add{id}")
    public ResponseEntity<Anime> addAnime(@RequestBody Anime anime) {
        Anime savedAnime = animeRepository.save(anime);
        return ResponseEntity.ok(savedAnime);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        return animeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable Long id, @RequestBody Anime animeDetails) {
        return animeRepository.findById(id).map(anime -> {
            anime.setTitle(animeDetails.getTitle());
            anime.setStatus(animeDetails.getStatus());
            anime.setProgress(animeDetails.getProgress());
            anime.setGenre(animeDetails.getGenre());
            anime.setRating(animeDetails.getRating());
            anime.setFavourites(animeDetails.isFavourites());
            return ResponseEntity.ok(animeRepository.save(anime));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAnime(@PathVariable Long id) {
        return animeRepository.findById(id).map(anime -> {
            animeRepository.delete(anime);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }


}
