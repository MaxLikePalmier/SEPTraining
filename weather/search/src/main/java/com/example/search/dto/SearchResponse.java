package com.example.search.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SearchResponse {
    List<bookResponse> books;
    String port;
    Timestamp timestamp;
}
