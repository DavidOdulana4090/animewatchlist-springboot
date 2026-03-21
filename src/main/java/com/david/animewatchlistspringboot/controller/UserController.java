package com.david.animewatchlistspringboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, Object> userData) {
        System.out.println("Email: " + userData.get("email"));
        System.out.println("Password: " + userData.get("password"));
        return ResponseEntity.ok("Success!");
    }
}