package com.example.userservice.model;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
@Repository
@FeignClient("microservice-one")
public interface ServiceOneClient {
    @GetMapping("api/microservice1/hi")
    public String hi();

}
