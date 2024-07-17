package com.alurachallenge.LiterAlura_LS.modelo;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
//import javax.persistence.*;

@Data
@Entity
@Table(name = "authors")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthYear;
}