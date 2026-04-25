package com.david.animewatchlistspringboot.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AnimeDTO {

    private UUID userId;
    private Long id;
    private String title;
    private String status = String.valueOf(AnimeEnum.WATCHING);
    private Integer progress = 0;
    private Double rating = 0.0;
    private Boolean isFavourite = false;
    private List<String> genres = List.of();

}
