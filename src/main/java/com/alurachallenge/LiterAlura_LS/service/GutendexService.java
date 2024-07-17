package com.alurachallenge.LiterAlura_LS.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GutendexService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public GutendexResponse searchBooks(String query) {
        String url = "https://gutendex.com/books?search=" + query;
        String response = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readValue(response, GutendexResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}