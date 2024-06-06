package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class University {

    @JsonProperty("domains")
    private List<String> domains;

    @JsonProperty("name")
    private String name;

    @JsonProperty("web_pages")
    private List<String> webPages;

}

