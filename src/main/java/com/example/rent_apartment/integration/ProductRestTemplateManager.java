package com.example.rent_apartment.integration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductRestTemplateManager {

    public void prepareProduct(@RequestParam Long id) {

        RestTemplate restTemplate = new RestTemplate();

        String path = "http://localhost:8097/product/add?id=%s";
        restTemplate.exchange(String.format(path, id),
                HttpMethod.GET,
                new HttpEntity<>(null),
                void.class).getBody();

    }
}
