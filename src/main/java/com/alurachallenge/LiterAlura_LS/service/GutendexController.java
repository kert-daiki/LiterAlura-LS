package com.alurachallenge.LiterAlura_LS.service;


import com.alurachallenge.LiterAlura_LS.modules.Author;
import com.alurachallenge.LiterAlura_LS.modules.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GutendexController {

    private final GutendexService gutendexService;

    @Autowired
    public GutendexController(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        GutendexResponse response = gutendexService.searchBooks(query);
        if (response == null || response.getResults().isEmpty()) {
            return "No se encontraron resultados.";
        }

        Book book = response.getResults().get(0);
        StringBuilder result = new StringBuilder();
        result.append("Título: ").append(book.getTitle()).append("\n");
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            Author author = book.getAuthors().get(0);
            result.append("Autor: ").append(author.getName()).append("\n");
            result.append("Año de Nacimiento: ").append(author.getBirthYear()).append("\n");
        }
        result.append("Idiomas: ").append(String.join(", ", book.getLanguages())).append("\n");
        return result.toString();
    }
}