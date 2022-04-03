package com.biblioteca.demobiblioteca.repositories;

import com.biblioteca.demobiblioteca.collections.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibroRepository extends MongoRepository<Libro, String> {
}
