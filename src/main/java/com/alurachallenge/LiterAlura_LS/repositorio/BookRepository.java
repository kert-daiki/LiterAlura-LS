package com.alurachallenge.LiterAlura_LS.repositorio;


import com.alurachallenge.LiterAlura_LS.modelo.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
