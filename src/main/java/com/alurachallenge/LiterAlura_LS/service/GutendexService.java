package com.alurachallenge.LiterAlura_LS.service;

import com.alurachallenge.LiterAlura_LS.modelo.AuthorEntity;
import com.alurachallenge.LiterAlura_LS.modelo.BookEntity;
import com.alurachallenge.LiterAlura_LS.modules.Author;
import com.alurachallenge.LiterAlura_LS.modules.Book;
import com.alurachallenge.LiterAlura_LS.repositorio.AuthorRepository;
import com.alurachallenge.LiterAlura_LS.repositorio.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public GutendexService(RestTemplate restTemplate, ObjectMapper objectMapper, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public GutendexResponse searchBooks(String query) {
        String url = "https://gutendex.com/books?search=" + query;
        String response = restTemplate.getForObject(url, String.class);
        try {
            GutendexResponse gutendexResponse = objectMapper.readValue(response, GutendexResponse.class);
            saveBooks(gutendexResponse.getResults());
            return gutendexResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveBooks(List<Book> books) {
        for (Book book : books) {
            List<AuthorEntity> authors = book.getAuthors().stream().map(this::toAuthorEntity).collect(Collectors.toList());
            List<AuthorEntity> savedAuthors = authorRepository.saveAll(authors);

            BookEntity bookEntity = new BookEntity();
            bookEntity.setTitle(book.getTitle());
            bookEntity.setAuthors(savedAuthors);
            bookEntity.setLanguages(book.getLanguages());

            bookRepository.save(bookEntity);
        }
    }

    private AuthorEntity toAuthorEntity(Author author) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(author.getName());
        authorEntity.setBirthYear(author.getBirthYear());
        return authorEntity;
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }
}