package com.example.userservice.controllers;


import com.example.userservice.entities.UserVO;
import com.example.userservice.model.ServiceOneClient;
import com.example.userservice.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private ServiceOneClient serviceOneClient;

    @PostMapping
    public ResponseEntity<UserVO> save(@RequestBody UserVO userVO){
        return ResponseEntity.ok(userService.save(userVO));
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("Hello, from secured endpoint!");
    }
    @GetMapping("/ms1")
    public ResponseEntity<String> MS1Endpoint() {
        String res = serviceOneClient.hi();
        return ResponseEntity.ok("Received response from  Microservice 1 :"+res);
    }



}
