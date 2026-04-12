package com.david.animewatchlistspringboot.DTO;

import lombok.Getter;

@Getter
public class ProfileDTO {
    private String email;
    private String username;
    private String userId;
    private String createdAt;

    public ProfileDTO(String email, String username, String createdAt, String userId) {
        this.email = email;
        this.username = username;
        this.createdAt = createdAt;
        this.userId = userId;
    }

}
