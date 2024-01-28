package com.example.pja.services;

import com.example.pja.repositories.MicroserviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.pja.controllers.data.Microservice;

import java.util.List;

@Service
public class MicroserviceService {

    private final MicroserviceRepository microserviceRepository;

    // Pridaná inicializácia RestTemplate
    private final RestTemplate restTemplate = new RestTemplate();

    public MicroserviceService(MicroserviceRepository microserviceRepository) {
        this.microserviceRepository = microserviceRepository;
    }

    public Microservice addPost(Microservice post) {
        // Validujte userId pomocou externého API
        final String validateUserUrl = "https://jsonplaceholder.typicode.com/users/" + post.getUserId();
        try {
            restTemplate.getForObject(validateUserUrl, Object.class);
            // Ak je userId platný, uložíme príspevok
            return microserviceRepository.save(post);
        } catch (HttpClientErrorException.NotFound e) {
            // V prípade výnimky, ktorá indikuje neplatné userId
            throw new RuntimeException("Invalid userId");
        }
    }


    public List<Microservice> getPostsByUserId(Integer userId) {
        return microserviceRepository.findByUserId(userId);
    }


}