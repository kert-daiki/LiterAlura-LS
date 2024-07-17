package com.alurachallenge.LiterAlura_LS.ejecucion;



import com.alurachallenge.LiterAlura_LS.modelo.AuthorEntity;
import com.alurachallenge.LiterAlura_LS.modelo.BookEntity;
import com.alurachallenge.LiterAlura_LS.modules.Author;
import com.alurachallenge.LiterAlura_LS.modules.Book;
import com.alurachallenge.LiterAlura_LS.service.GutendexResponse;
import com.alurachallenge.LiterAlura_LS.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final GutendexService gutendexService;

    public CommandLineAppStartupRunner(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Buscar Libro o Autor");
            System.out.println("2. Listar Libros");
            System.out.println("3. Listar Autores");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Ingrese el término de búsqueda: ");
                String query = scanner.nextLine();
                GutendexResponse response = gutendexService.searchBooks(query);
                if (response == null || response.getResults().isEmpty()) {
                    System.out.println("No se encontraron resultados.");
                } else {
                    Book book = response.getResults().get(0);
                    BookEntity bookEntity = convertToBookEntity(book);
                    StringBuilder result = new StringBuilder();
                    result.append("Título: ").append(bookEntity.getTitle()).append("\n");
                    if (bookEntity.getAuthors() != null && !bookEntity.getAuthors().isEmpty()) {
                        AuthorEntity author = bookEntity.getAuthors().get(0);
                        result.append("Autor: ").append(author.getName()).append("\n");
                        result.append("Año de Nacimiento: ").append(author.getBirthYear()).append("\n");
                    }
                    result.append("Idiomas: ").append(String.join(", ", bookEntity.getLanguages())).append("\n");
                    System.out.println("Resultado: " + result.toString());
                }
            } else if (choice == 2) {
                List<BookEntity> books = gutendexService.getAllBooks();
                for (BookEntity book : books) {
                    System.out.println("Título: " + book.getTitle());
                    for (AuthorEntity author : book.getAuthors()) {
                        System.out.println("Autor: " + author.getName());
                    }
                    System.out.println("Idiomas: " + String.join(", ", book.getLanguages()));
                    System.out.println();
                }
            } else if (choice == 3) {
                List<AuthorEntity> authors = gutendexService.getAllAuthors();
                for (AuthorEntity author : authors) {
                    System.out.println("Nombre: " + author.getName());
                    System.out.println("Año de Nacimiento: " + author.getBirthYear());
                    System.out.println();
                }
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private BookEntity convertToBookEntity(Book book) {
        List<AuthorEntity> authors = book.getAuthors().stream().map(this::toAuthorEntity).collect(Collectors.toList());
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthors(authors);
        bookEntity.setLanguages(book.getLanguages());
        return bookEntity;
    }

    private AuthorEntity toAuthorEntity(com.alurachallenge.LiterAlura_LS.modules.Author author) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(author.getName());
        authorEntity.setBirthYear(author.getBirthYear());
        return authorEntity;
    }
}