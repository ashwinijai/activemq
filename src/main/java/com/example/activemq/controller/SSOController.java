package com.example.activemq.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSOController {

    @GetMapping("/validate")
    public ResponseEntity<String> validateUser(@PathVariable("username") String userName, @PathVariable("password") String password){
        if (userName.equals("user")&& userName.equals("pass")){
            return ResponseEntity.ok().body("Success");
        }
        return ResponseEntity.internalServerError().body("Invalid creds");
    }

}
