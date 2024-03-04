package com.example.microservicetwo.controller;


import com.example.microservicetwo.model.ServiceOneClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/microservice2")
public class HelloController {


    @Autowired
    private ServiceOneClient serviceOneClient;
    @GetMapping("/call-mssv1")
    public String greet(){
        //call Micro
        String res = serviceOneClient.hi();
    return "Received response from  Microservice 1 :" + res;
}
    @GetMapping("/bonjour")
    public String bonjour (){
        return "BONJOUR FROM SERVICE 2";
}

}
