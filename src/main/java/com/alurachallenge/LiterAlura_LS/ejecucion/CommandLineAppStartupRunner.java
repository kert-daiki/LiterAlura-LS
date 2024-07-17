package com.alurachallenge.LiterAlura_LS.ejecucion;



import com.alurachallenge.LiterAlura_LS.modules.Author;
import com.alurachallenge.LiterAlura_LS.modules.Book;
import com.alurachallenge.LiterAlura_LS.service.GutendexResponse;
import com.alurachallenge.LiterAlura_LS.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

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
            System.out.println("2. Salir");
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
                    StringBuilder result = new StringBuilder();
                    result.append("Título: ").append(book.getTitle()).append("\n");
                    if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
                        Author author = book.getAuthors().get(0);
                        result.append("Autor: ").append(author.getName()).append("\n");
                        result.append("Año de Nacimiento: ").append(author.getBirthYear()).append("\n");
                    }
                    result.append("Idiomas: ").append(String.join(", ", book.getLanguages())).append("\n");
                    System.out.println("Resultado: " + result.toString());
                }
            } else if (choice == 2) {
                break;
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
}