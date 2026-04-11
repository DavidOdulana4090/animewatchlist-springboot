package com.david.animewatchlistspringboot.controller;

import com.david.animewatchlistspringboot.DTO.ProfileDTO;
import com.david.animewatchlistspringboot.config.SecurityConfig;
import com.david.animewatchlistspringboot.entity.User;
import com.david.animewatchlistspringboot.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        user.setCreatedAt(LocalDate.now());
        user.setPassword(encoded);

        DatabaseRepository.save(user);
        return ResponseEntity.ok("User created Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> loginUser(@RequestBody Map<String, Object> userData) {
        String email = userData.get("email").toString();
        String password = userData.get("password").toString();

        if (!DatabaseRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new ProfileDTO(email, "", "", ""));
        }

        User user = DatabaseRepository.findByEmail(email);

        if (!securityConfig.passwordEncoder().matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body(new ProfileDTO(email, "", "", ""));
        }

        ProfileDTO profileDTO = new ProfileDTO(
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt().toString(),
                user.getId().toString()
        );
        return ResponseEntity.ok(profileDTO);
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, Object> userData) {
        String email = userData.get("email").toString();
        if(!DatabaseRepository.existsByEmail(email)){
            return ResponseEntity.badRequest().body("ERROR ");
        }
        return ResponseEntity.ok("Logout Successful");

    }

    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> getInfo(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ProfileDTO(user.getEmail(), user.getUsername(),
                user.getCreatedAt().toString(), user.getId().toString()));
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<ProfileDTO> displaybyId(@PathVariable long id) {
        User user = DatabaseRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ProfileDTO(user.getEmail(), user.getUsername(),
                user.getCreatedAt().toString(), user.getId().toString()));
    }

}