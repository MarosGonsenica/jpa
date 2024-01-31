package com.example.pja.controllers;

import com.example.pja.controllers.data.Microservice;
import com.example.pja.services.MicroserviceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MicroserviceController {

    @Autowired
    private final MicroserviceService microserviceService;

    @Autowired
    public MicroserviceController(MicroserviceService microserviceService) {
        this.microserviceService = microserviceService;
    }

    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody Microservice post) {
        try {
            Microservice newPost = microserviceService.createPost(post);
            return ResponseEntity.ok(newPost);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getPostsByUserId")
    public ResponseEntity<?> getPostsByUserId(@RequestParam Integer userId) {
        try {
            List<Microservice> posts = microserviceService.getPostsByUserId(userId);
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No posts found for userId " +userId);
            }
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getPostById")
    public ResponseEntity<?> getPostById(@RequestParam Integer id) {
        try {
            Microservice post = microserviceService.getOrCreatePostById(id);
            return ResponseEntity.ok(post);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }


    @DeleteMapping("/deletePostById")
    public void deletePost(@RequestParam Integer id) {
        microserviceService.deletePostById(id);
    }

    @PutMapping("/updatePostById/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Integer id,
            @RequestBody Microservice microservice) {
        try {
            Microservice updatedPost = microserviceService.updatePost(id, microservice.getTitle(), microservice.getBody());
            return ResponseEntity.ok(updatedPost);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}
