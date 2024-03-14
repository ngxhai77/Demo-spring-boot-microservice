package com.example.authenticationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {

    private String fullName;
    private String userName;
    private String password;
    private String phone;
    private String address;
    private String  role;
}
