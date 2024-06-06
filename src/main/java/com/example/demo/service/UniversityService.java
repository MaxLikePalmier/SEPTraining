package com.example.demo.service;

import com.example.demo.entity.University;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UniversityService {
    List<University> findAll();

    CompletableFuture<List<University>> findByCountryList(List<String> country);
}
