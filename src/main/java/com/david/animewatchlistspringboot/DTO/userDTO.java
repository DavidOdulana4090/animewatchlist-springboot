package com.david.animewatchlistspringboot.DTO;

public class userDTO {
    private String email;
    private String username;
    private String password;
    private String createdAt;

    public userDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
