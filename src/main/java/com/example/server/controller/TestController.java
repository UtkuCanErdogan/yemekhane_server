package com.example.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public ResponseEntity<String> get(){
        String str = "Spring > .Net";
        return ResponseEntity.ok(str);
    }
}
