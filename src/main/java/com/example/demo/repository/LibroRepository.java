package com.example.demo.repository;

import com.example.demo.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Este repositorio nos permite hacer operaciones en la tabla libros
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    // Metodo para buscar libros por titulo (busqueda parcial)
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    // Metodo para buscar libros por autor
    List<Libro> findByAutorContainingIgnoreCase(String autor);
}
