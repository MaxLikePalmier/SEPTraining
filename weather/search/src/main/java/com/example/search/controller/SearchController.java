package com.example.search.controller;

import com.example.search.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

//    @RequestBody List<String> author
    @GetMapping("/weather/search")
    public ResponseEntity<?> getDetails() {
        return new ResponseEntity<>(searchService.search(List.of(1)), HttpStatus.OK);
    }
}
