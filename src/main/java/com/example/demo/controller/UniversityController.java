package com.example.demo.controller;

import com.example.demo.entity.University;
import com.example.demo.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("university")
public class UniversityController {
    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public ResponseEntity<List<University>> findAll() {
        List<University> universities = universityService.findAll();
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }

    @GetMapping("country")
    public ResponseEntity<List<University>> findByCountryList(@RequestBody List<String> country) {
        CompletableFuture<List<University>> universitiesFuture = universityService.findByCountryList(country);
        try {
            List<University> universities = universitiesFuture.get();
            return new ResponseEntity<>(universities, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
