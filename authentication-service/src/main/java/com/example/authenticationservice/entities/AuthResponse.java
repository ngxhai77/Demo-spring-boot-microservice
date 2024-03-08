package com.example.authenticationservice.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    public AuthResponse(String accessToken, String refreshToken ){
        this.accessToken = accessToken ;

    }
}
