package com.david.animewatchlistspringboot.controller;

import com.david.animewatchlistspringboot.DTO.CreateAccountDTO;
import com.david.animewatchlistspringboot.DTO.LoginDTO;
import com.david.animewatchlistspringboot.DTO.ProfileDTO;
import com.david.animewatchlistspringboot.config.SecurityConfig;
import com.david.animewatchlistspringboot.entity.User;
import com.david.animewatchlistspringboot.repository.UserRepository;
import com.david.animewatchlistspringboot.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository DatabaseRepository;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateAccountDTO createAccountDTO) {
        if (DatabaseRepository.existsByEmail(createAccountDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }
        String encoded = securityConfig.passwordEncoder().encode(createAccountDTO.getPassword());

        User user = new User();
        user.setEmail(createAccountDTO.getEmail());
        user.setCreatedAt(LocalDate.now());
        user.setPassword(encoded);
        user.setUsername(createAccountDTO.getEmail().split("@")[0]);

        DatabaseRepository.save(user);
        return ResponseEntity.ok("User created Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        if (!DatabaseRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Incorrect Password or Email");
        }

        User user = DatabaseRepository.findByEmail(email);

        if (!securityConfig.passwordEncoder().matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body("Incorrect Password or Email");
        }

        ProfileDTO profileDTO = new ProfileDTO(
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt().toString(),
                user.getUuid(),
                jwtService.generateToken(email)
        );
        return ResponseEntity.ok(profileDTO);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        if (!DatabaseRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email does not exist");
        } else {
            User user = DatabaseRepository.findByEmail(email);
            return ResponseEntity.ok("Password Reset Screen");
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> SetNewPassword(@RequestBody LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
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

    // Test endpoint
    @GetMapping("/api/users/{uuid}")
    public ResponseEntity<User> displaybyId(@PathVariable UUID uuid) {
        User user = DatabaseRepository.findById(uuid).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String jwtToken = token.substring(7);
        String email = jwtService.extractEmail(jwtToken);
        User user = DatabaseRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        ProfileDTO profileDTO = new ProfileDTO(
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt().toString(),
                user.getUuid(),
                jwtToken
        );
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/token/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String jwtToken = token.substring(7);
        boolean isValid = jwtService.isTokenValid(jwtToken);
        if (isValid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}