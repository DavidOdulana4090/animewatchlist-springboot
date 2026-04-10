package com.david.animewatchlistspringboot.DTO;

public class ProfileDTO {
    private String email;
    private String username;
    private String createdAt;

    public ProfileDTO(String email, String username, String createdAt) {
        this.email = email;
        this.username = username;
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
