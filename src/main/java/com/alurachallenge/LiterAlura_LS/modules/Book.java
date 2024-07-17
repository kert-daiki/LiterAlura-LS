package com.alurachallenge.LiterAlura_LS.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String title;
    @JsonProperty("authors")
    private List<Author> authors;
    @JsonProperty("languages")
    private List<String> languages;
}