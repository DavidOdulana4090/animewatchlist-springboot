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
    private String status;
    private Integer progress;
    private Double rating;
    private Boolean isFavourite;
    private List<String> genres;

}
