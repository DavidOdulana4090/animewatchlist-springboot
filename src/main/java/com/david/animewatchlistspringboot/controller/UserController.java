package com.david.animewatchlistspringboot.controller;

import com.david.animewatchlistspringboot.config.SecurityConfig;
import com.david.animewatchlistspringboot.entity.User;
import com.david.animewatchlistspringboot.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private DatabaseRepository DatabaseRepository;
    @Autowired
    private SecurityConfig securityConfig;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, Object> userData) {
        if (DatabaseRepository.existsByEmail(userData.get("email").toString())){
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        String encoded = securityConfig.passwordEncoder().encode(userData.get("password").toString());

        User user = new User();
        user.setEmail(userData.get("email").toString());
        user.setPassword(encoded);

        DatabaseRepository.save(user);
        return ResponseEntity.ok("User created Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, Object> userData) {
        if (!DatabaseRepository.existsByEmail(userData.get("email").toString())){
            return ResponseEntity.badRequest().body("Email does not exist!");
        } else {
            User user = DatabaseRepository.findByEmail(userData.get("email").toString());
            if (securityConfig.passwordEncoder().matches(userData.get("password").toString(), user.getPassword())){
                return ResponseEntity.ok("Login successful");
            }
        }
        return ResponseEntity.badRequest().body("Invalid Password");
    }


}