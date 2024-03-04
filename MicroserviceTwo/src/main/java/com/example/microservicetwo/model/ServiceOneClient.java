package com.example.microservicetwo.model;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("microservice-one")
public interface ServiceOneClient {
    @GetMapping("api/microservice1/hi")
    public String hi();

}
