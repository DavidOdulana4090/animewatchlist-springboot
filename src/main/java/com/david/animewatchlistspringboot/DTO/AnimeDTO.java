package com.david.animewatchlistspringboot.DTO;

import com.david.animewatchlistspringboot.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
    private String genre;

}
