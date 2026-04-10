package com.david.animewatchlistspringboot.DTO;

public class AnimeDTO {
    private Long id;
    private String title;
    private String status;
    private Integer progress;
    private Double rating;
    private Boolean favourites;
    private String genre;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setFavourites(Boolean favourites) {
        this.favourites = favourites;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public Integer getProgress() {
        return progress;
    }

    public Double getRating() {
        return rating;
    }

    public Boolean getFavourites() {
        return favourites;
    }

    public String getGenre() {
        return genre;
    }
}
