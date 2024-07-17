package com.alurachallenge.LiterAlura_LS.repositorio;

import  com.alurachallenge.LiterAlura_LS.modelo.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
