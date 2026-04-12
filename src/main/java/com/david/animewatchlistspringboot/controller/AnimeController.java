package com.david.animewatchlistspringboot.controller;


import com.david.animewatchlistspringboot.DTO.AnimeDTO;
import com.david.animewatchlistspringboot.entity.Anime;
import com.david.animewatchlistspringboot.entity.User;
import com.david.animewatchlistspringboot.repository.AnimeRepository;
import com.david.animewatchlistspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private UserRepository UserRepository;


    @GetMapping("/all/{Id}")
    public List<AnimeDTO> findUserbyId(@PathVariable Long Id) {
        User user = UserRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return animeRepository.findByUserId(Id).stream()
                .map(anime -> {
                    AnimeDTO animeDTO = new AnimeDTO();
                    animeDTO.setUserId(user.getId());
                    animeDTO.setId(anime.getId());
                    animeDTO.setTitle(anime.getTitle());
                    animeDTO.setStatus(anime.getStatus());
                    animeDTO.setProgress(anime.getProgress());
                    animeDTO.setGenre(anime.getGenre());
                    animeDTO.setRating(anime.getRating());
                    animeDTO.setIsFavourite(anime.isFavourites());
                    return animeDTO;
                }).toList();
    }

    @PostMapping("/add{id}")
    public ResponseEntity<Anime> addAnime(@RequestBody AnimeDTO animeDTO, @RequestParam Long id) {
        Anime newAnime = new Anime();
        newAnime.setTitle(animeDTO.getTitle());
        newAnime.setStatus(animeDTO.getStatus());
        newAnime.setProgress(animeDTO.getProgress());
        newAnime.setGenre(animeDTO.getGenre());
        newAnime.setRating(animeDTO.getRating());
        newAnime.setFavourites(animeDTO.getIsFavourite());
        User user = UserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        newAnime.setUser(user);  // Set the user for the anime

        Anime savedAnime = animeRepository.save(newAnime);
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
