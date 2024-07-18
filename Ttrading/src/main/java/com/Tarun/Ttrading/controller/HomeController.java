package com.Tarun.Ttrading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "WelCome to treading Platform";
    }
    @GetMapping("/api")
    public String secure(){
        return "WelCome to treading Platform secure";
    }
}
