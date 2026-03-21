package com.david.animewatchlistspringboot.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ResponseController {

    @RequestMapping("/api/response")
    public String getResponse(){
        return "Hello World";
    }
}
