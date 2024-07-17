package com.alurachallenge.LiterAlura_LS.service;

import com.alurachallenge.LiterAlura_LS.modules.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexResponse {
    private List<Book> results;
}