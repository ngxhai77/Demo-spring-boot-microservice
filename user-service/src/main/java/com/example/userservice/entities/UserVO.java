package com.example.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

    public String id ;
    public String email ;
    public String password ;
    public String role ;
}