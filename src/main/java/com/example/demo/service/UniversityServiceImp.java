package com.example.demo.service;

import com.example.demo.entity.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class UniversityServiceImp implements UniversityService {

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    @Value("${url}")
    private String url;

    @Autowired
    public UniversityServiceImp(RestTemplate restTemplate, ExecutorService executorService) {
        this.restTemplate = restTemplate;
        this.executorService = executorService;
    }

    @Override
    public List<University> findAll() {
        University[] arr = restTemplate.getForObject(url, University[].class);
        return Arrays.asList(arr);
    }

    @Override
    public List<University> findByCountryList(List<String> countryList) {
        List<CompletableFuture<List<University>>> futureList = countryList.stream()
                .map(country -> CompletableFuture.supplyAsync(() -> findUniversitiesBySingleCountry(country),executorService))
                .toList();
        List<University> list = new ArrayList<>();
        CompletableFuture.allOf(futureList.toArray(CompletableFuture[]::new))
                .thenAccept(x -> futureList.forEach(future -> list.addAll(future.join()))).join();
        return list;
    }

    private List<University> findUniversitiesBySingleCountry(String country) {
        University[] universityArray = restTemplate.getForObject(url + "?country=" + country.replaceAll("\\s+", "+"), University[].class);
        return Arrays.asList(universityArray);
    }

}
