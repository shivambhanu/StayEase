package com.backend.hotel.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello-user")
    public String helloUser(){
        return "Hello USER";
    }


    @GetMapping("/hello-admin")
    public String helloAdmin(){
        return "Hello ADMIN";
    }
}
