package com.david.animewatchlistspringboot.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "anime")
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String status;
    private int progress;
    private List<String> genres;
    private double rating;
    private boolean favourite;

    public Anime(String title) {
        this.title = title;
    }

}
