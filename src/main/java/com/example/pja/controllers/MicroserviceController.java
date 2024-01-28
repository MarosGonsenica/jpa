package com.example.pja.controllers;

import com.example.pja.controllers.data.Microservice;
import com.example.pja.services.MicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MicroserviceController {

    @Autowired
    private final MicroserviceService microserviceService;

    public MicroserviceController(MicroserviceService microserviceService) {
        this.microserviceService = microserviceService;
    }

    @PostMapping("/createPost")
    public ResponseEntity<Microservice> createPost(@RequestBody Microservice post) {
        try {
            Microservice newPost = microserviceService.addPost(post);
            return ResponseEntity.ok(newPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getPostByUserId")
    public List<Microservice> getPostsByUserId(
            @RequestParam Integer userId) {
    // TODO: throw exception
        return  microserviceService.getPostsByUserId(userId);
    }
}
