package com.alurachallenge.LiterAlura_LS.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    private String name;
    @JsonProperty("birth_year")
    private Integer birthYear;
}