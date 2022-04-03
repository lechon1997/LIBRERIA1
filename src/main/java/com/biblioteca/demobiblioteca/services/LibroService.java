package com.biblioteca.demobiblioteca.services;

import com.biblioteca.demobiblioteca.collections.Libro;
import com.biblioteca.demobiblioteca.dto.LibroDTO;
import com.biblioteca.demobiblioteca.mappers.LibroMapper;
import com.biblioteca.demobiblioteca.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository repository;
    private LibroMapper mapper = new LibroMapper();

    public List<LibroDTO> obtenerTodos(){
        List<Libro> libros = (List<Libro>) repository.findAll();
        return mapper.fromCollectionList(libros);
    }

    public LibroDTO obtenerPorId(String id){
        Libro libro = repository.findById(id).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return mapper.fromCollection(libro);
    }

    public LibroDTO crear(LibroDTO libroDTO){
        Libro libro = mapper.fromDTO(libroDTO);
        return mapper.fromCollection(repository.save(libro));
    }

    public LibroDTO modificar(LibroDTO libroDTO){
        Libro libro = mapper.fromDTO(libroDTO);
        repository.findById(libro.getId()).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return mapper.fromCollection(repository.save(libro));
    }

    public void borrar(String id){
        repository.deleteById(id);
    }



}
