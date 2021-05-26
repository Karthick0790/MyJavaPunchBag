package com.example.jwtDemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @RequestMapping("/hello")
    public String welcome() {
        return "Welcome to JWT Demo";
    }
}
