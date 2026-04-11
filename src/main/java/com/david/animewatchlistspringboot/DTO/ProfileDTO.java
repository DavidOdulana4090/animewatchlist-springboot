package com.david.animewatchlistspringboot.DTO;

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

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUserId() {
        return userId;
    }
}
