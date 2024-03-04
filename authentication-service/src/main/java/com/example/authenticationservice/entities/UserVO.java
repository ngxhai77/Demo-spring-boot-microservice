package com.example.authenticationservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    public String id ;
    public String email ;
    public String password ;
    public String role ;
}
