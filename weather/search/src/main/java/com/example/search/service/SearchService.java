package com.example.search.service;

import com.example.search.dto.SearchResponse;

import java.util.List;

public interface SearchService {
    SearchResponse search(List<Integer> authorList);
}
