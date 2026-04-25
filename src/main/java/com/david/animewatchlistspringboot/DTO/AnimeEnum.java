package com.david.animewatchlistspringboot.DTO;

public enum AnimeEnum {

    WATCHING("Watching"),
    COMPLETED("Completed"),
    DROPPED("Dropped"),
    PLANNING("Planning");

    private final String status;
    AnimeEnum(String status) {
        this.status = status;
    }


}
