//package com.david.animewatchlistspringboot.service;
//
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class JwtService {
//
//    @Value("jwt.secret")
//    private String key;
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//    }
//}
