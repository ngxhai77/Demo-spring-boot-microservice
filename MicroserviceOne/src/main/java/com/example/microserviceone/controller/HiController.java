package com.example.microserviceone.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/microservice1")
public class HiController {

    @Autowired
    private Environment environment;


    @GetMapping("/hi")
    public String hi(){
        return "Hello world from microservice 1, port = "+ environment.getProperty("local.server.port");
    }



}
