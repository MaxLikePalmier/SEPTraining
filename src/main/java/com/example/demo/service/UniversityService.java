package com.example.demo.service;

import com.example.demo.entity.University;

import java.util.List;

public interface UniversityService {
    List<University> findAll();

    List<University> findByCountryList(List<String> country);
}
