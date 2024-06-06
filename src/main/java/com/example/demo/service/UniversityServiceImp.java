package com.example.demo.service;

import com.example.demo.entity.University;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UniversityServiceImp implements UniversityService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UniversityServiceImp(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<University> findAll() {
        String response = restTemplate.getForObject("http://universities.hipolabs.com/search", String.class);
        try {
            List<University> list = objectMapper.readValue(response, new TypeReference<>() {
            });
            return list.stream()
                    .map(
                            university -> new University(university.getDomains(), university.getName(), university.getWebPages())
                    )
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<List<University>> findByCountryList(List<String> countryList) {
        List<CompletableFuture<List<University>>> futureList = countryList.stream()
                .map(country -> CompletableFuture.supplyAsync(() -> findUniversitiesBySingleCountry(country)))
                .collect(Collectors.toList());

        CompletableFuture<Void> all = CompletableFuture.allOf(futureList.toArray(CompletableFuture[]::new));
        List<University> list = new ArrayList<>();
        return all.thenApply(x-> {
            futureList.stream().forEach(future -> {
                try {
                    list.addAll(future.get());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return list;
        });
    }

    private List<University> findUniversitiesBySingleCountry(String country) {
        String url = "http://universities.hipolabs.com/search?country=" + country.replaceAll("\\s+", "+");
        String response = restTemplate.getForObject(url, String.class);

        try {
            return objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
