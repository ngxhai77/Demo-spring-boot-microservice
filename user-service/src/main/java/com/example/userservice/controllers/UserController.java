package com.example.userservice.controllers;


import com.example.userservice.entities.UserVO;
import com.example.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserVO> save(@RequestBody UserVO userVO){
        return ResponseEntity.ok(userService.save(userVO));
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("Hello, from secured endpoint!");
    }

}
