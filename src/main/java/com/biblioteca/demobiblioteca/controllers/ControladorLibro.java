package com.biblioteca.demobiblioteca.controllers;

import com.biblioteca.demobiblioteca.dto.DisponibilidadDTO;
import com.biblioteca.demobiblioteca.dto.LibroDTO;
import com.biblioteca.demobiblioteca.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/sofka-libreria")
public class ControladorLibro {

    @Autowired
    private LibroService service;

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> findById(@PathVariable("id") String id){
        return new ResponseEntity(service.obtenerPorId(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LibroDTO>> findAll(){
        return new ResponseEntity(service.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LibroDTO> create(@RequestBody LibroDTO libroDTO){
        return new ResponseEntity(service.crear(libroDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<LibroDTO> update(@RequestBody LibroDTO libroDTO){
        if(libroDTO.getId() != null){
            return new ResponseEntity(service.modificar(libroDTO), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        try {
            service.borrar(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/prestar/{id}")
    public ResponseEntity<LibroDTO> prestar(@PathVariable("id") String id){

        LibroDTO libro = service.obtenerPorId(id);
        if(libro.isDisponible()){
            libro.setDisponible(false);
            libro.setFechaPrestamo(LocalDate.now());
            return new ResponseEntity(service.modificar(libro), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<String> devolver(@PathVariable("id") String id){

        LibroDTO libro = service.obtenerPorId(id);
        if(!libro.isDisponible()){
            libro.setDisponible(true);
            service.modificar(libro);
            return new ResponseEntity("Libro devuelto", HttpStatus.OK);
        }
        return new ResponseEntity("El libro no se puede devolver porque está disponible", HttpStatus.FORBIDDEN);
    }

    @GetMapping("disponibilidad/{id}")
    public ResponseEntity<DisponibilidadDTO> disponibilidad(@PathVariable("id") String id) {
        LibroDTO libro = service.obtenerPorId(id);
        DisponibilidadDTO disponibilidadDTO = new DisponibilidadDTO();
        disponibilidadDTO.setDisponible(libro.isDisponible());
        if(!libro.isDisponible()){
            disponibilidadDTO.setFechaPrestamo(libro.getFechaPrestamo());
        }

        return new ResponseEntity(disponibilidadDTO, HttpStatus.OK);
    }


}
