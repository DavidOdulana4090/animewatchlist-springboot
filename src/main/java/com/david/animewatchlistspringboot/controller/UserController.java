package com.david.animewatchlistspringboot.controller;

import com.david.animewatchlistspringboot.config.SecurityConfig;
import com.david.animewatchlistspringboot.entity.User;
import com.david.animewatchlistspringboot.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private DatabaseRepository DatabaseRepository;
    @Autowired
    private SecurityConfig securityConfig;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, Object> userData) {
        if (DatabaseRepository.existsByEmail(userData.get("email").toString())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        String encoded = securityConfig.passwordEncoder().encode(userData.get("password").toString());

        User user = new User();
        user.setEmail(userData.get("email").toString());
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setPassword(encoded);

        DatabaseRepository.save(user);
        return ResponseEntity.ok("User created Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, Object> userData) {
        if (!DatabaseRepository.existsByEmail(userData.get("email").toString())) {
            return ResponseEntity.badRequest().body("Email does not exist!");
        } else {
            User user = DatabaseRepository.findByEmail(userData.get("email").toString());
            if (securityConfig.passwordEncoder().matches(userData.get("password").toString(), user.getPassword())) {
                return ResponseEntity.ok("Login successful");
            }
        }
        return ResponseEntity.badRequest().body("Invalid Password");
    }

    @PutMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, Object> userData) {
        if (!DatabaseRepository.existsByEmail(userData.get("email").toString())) {
            return ResponseEntity.badRequest().body("Email does not exist");
        } else {
            User user = DatabaseRepository.findByEmail(userData.get("email").toString());
            return ResponseEntity.ok("Password Reset Screen");
        }
    }

    @PutMapping("/newpassword")
    public ResponseEntity<?> SetNewPassword(@RequestBody Map<String, Object> userData) {

        String email = userData.get("email").toString();
        String password = userData.get("password").toString();
        String hashpassword = securityConfig.passwordEncoder().encode(password);

        if (!DatabaseRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email does not exist");
        } else {
            User user = DatabaseRepository.findByEmail(email);
            user.setPassword(hashpassword);
            DatabaseRepository.save(user);
            return ResponseEntity.ok("Password Reset Successful");
        }
    }

    @PostMapping("/getusername")
    public ResponseEntity<?> getUsernameFromEmail(@RequestBody Map<String, Object> userData) {
        String email = userData.get("email").toString();
        if (!DatabaseRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email does not exist");
        } else {
            User user = DatabaseRepository.findByEmail(email);
            return ResponseEntity.ok(user.getUsername());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, Object> userData) {
        return ResponseEntity.ok("Logout Successful");

    }

    @GetMapping("/me")
    public ResponseEntity<?> getInfo(@RequestParam String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        } else if (!DatabaseRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email does not exist");
        } else {
            User user = DatabaseRepository.findByEmail(email);
            return ResponseEntity.ok(user);
        }
    }

}