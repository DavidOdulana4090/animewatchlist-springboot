package com.david.animewatchlistspringboot.DTO;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProfileDTO {
    private String email;
    private String username;
    private UUID userId;
    private String createdAt;

    public ProfileDTO(String email, String username, String createdAt, UUID userId) {
        this.email = email;
        this.username = username;
        this.createdAt = createdAt;
        this.userId = userId;
    }

}
