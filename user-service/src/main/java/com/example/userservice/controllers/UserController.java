package com.example.userservice.controllers;



import com.example.userservice.model.ServiceOneClient;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private ServiceOneClient serviceOneClient;


    @GetMapping(path = "/secured")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("Hello, from secured endpoint!");
    }


    @GetMapping(path = "/ms1")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> MS1Endpoint() {
        String res = serviceOneClient.hi();
        return ResponseEntity.ok("Received response from  Microservice 1 :" + res);
    }

}
