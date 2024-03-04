package com.example.authenticationservice.controllers;

import com.example.authenticationservice.entities.AuthRequest;
import com.example.authenticationservice.entities.AuthResponse;
import com.example.authenticationservice.services.AuthService;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService ;

    @PostMapping(value = "/register")
    public  ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
}
