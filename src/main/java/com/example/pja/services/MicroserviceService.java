package com.example.pja.services;

import com.example.pja.repositories.MicroserviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.pja.controllers.data.Microservice;

import java.util.List;
import java.util.Optional;

@Service
public class MicroserviceService {

    private final MicroserviceRepository microserviceRepository;

    // inicializácia RestTemplate
    private final RestTemplate restTemplate = new RestTemplate();

    public MicroserviceService(MicroserviceRepository microserviceRepository) {
        this.microserviceRepository = microserviceRepository;
    }

    public Microservice addPost(Microservice post) {
        // validuje userId pomocou externého API
        final String validateUserUrl = "https://jsonplaceholder.typicode.com/users/" + post.getUserId();
        try {
            restTemplate.getForObject(validateUserUrl, Object.class);
            // ak je userId platný, uloží príspevok
            return microserviceRepository.save(post);
        } catch (HttpClientErrorException.NotFound e) {
            // exception neplatné userId
            throw new RuntimeException("Invalid userId");
        }
    }


    public List<Microservice> getPostsByUserId(Integer userId) {
        return microserviceRepository.findByUserId(userId);
    }

    public Optional<Microservice> getPostById(Integer id) {
        // Skúste najprv nájsť príspevok v lokálnej databáze
        return microserviceRepository.findById(id);
    }

    public Microservice findPostByIdExternalAPI(Integer id) {
        final String validateIdUrl = "https://jsonplaceholder.typicode.com/posts/" + id;
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Microservice> response = restTemplate.getForEntity(validateIdUrl, Microservice.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (HttpClientErrorException e) {
            // Log chybu alebo ju spracujte podľa potreby
            System.out.println("External API did not find the post: " + e.getMessage());
        }
        return null; // Vráti null, ak sa príspevok nenájde
    }

    public Microservice savePost(Microservice post) {
        // Uloží príspevok do databázy
        return microserviceRepository.save(post);
    }
}