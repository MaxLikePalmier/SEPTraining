package com.example.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class bookResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

}
