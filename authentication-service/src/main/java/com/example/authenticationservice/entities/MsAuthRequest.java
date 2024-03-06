package com.example.authenticationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsAuthRequest {

    private String id;
    private String role;

}
