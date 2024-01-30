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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getPostsByUserId")
    public List<Microservice> getPostsByUserId(
            @RequestParam Integer userId) {

        return microserviceService.getPostsByUserId(userId);
    }

    @GetMapping("/getPostById")
    public ResponseEntity<?> getPostById(@RequestParam Integer id) {
        return microserviceService.getOrCreatePostById(id);
    }

    @DeleteMapping("/deletePostById")
    public void deletePost(@RequestParam Integer id) {
        microserviceService.deletePostById(id);
    }

    @PutMapping("/updatePostById/{id}")
    public ResponseEntity<?> updatePost(
            //mozno este upravit aby neboli title a body vyzadovane
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
        //alebo by sa dal zrobit nejaky global ExceptionHandler
    }
}
