package com.example.authenticationservice.services.impl;

import com.example.authenticationservice.entities.AuthResponse;
import com.example.authenticationservice.entities.LoginRequest;
import com.example.authenticationservice.entities.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public ResponseEntity<Object> register(RegisterRequest request);

    public AuthResponse login(LoginRequest request);

    public ResponseEntity<Object> isValidToken(String token, String role);

}
