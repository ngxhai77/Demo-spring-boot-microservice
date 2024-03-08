package com.example.authenticationservice.controllers;

import com.example.authenticationservice.entities.LoginRequest;
import com.example.authenticationservice.entities.RegisterRequest;
import com.example.authenticationservice.entities.AuthResponse;
import com.example.authenticationservice.services.AuthService;
import com.example.authenticationservice.services.impl.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final IAuthService authService ;

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping(value = "/login")
    public  ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/valid-token")
    public ResponseEntity<?> validToken(@RequestHeader("Authorization") String token, @RequestParam("role") String role) {
        return authService.isValidToken(token, role);
    }


}
