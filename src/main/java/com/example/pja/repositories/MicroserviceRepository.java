package com.example.pja.repositories;

import com.example.pja.controllers.data.Microservice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MicroserviceRepository extends JpaRepository<Microservice, Integer> {

    List<Microservice> findByUserId(Integer userId);
}
