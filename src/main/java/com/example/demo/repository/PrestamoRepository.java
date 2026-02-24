package com.example.demo.repository;

import com.example.demo.model.Libro;
import com.example.demo.model.Prestamo;
import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Este repositorio nos permite hacer operaciones en la tabla prestamos
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Metodo para buscar prestamos de un usuario especifico
    List<Prestamo> findByUsuario(Usuario usuario);

    // Metodo para buscar prestamos activos de un usuario
    List<Prestamo> findByUsuarioAndEstado(Usuario usuario, String estado);

    // Metodo para buscar todos los prestamos activos
    List<Prestamo> findByEstado(String estado);

    // Verificar si un usuario ya tiene un libro prestado activamente
    boolean existsByUsuarioAndLibroAndEstado(Usuario usuario, Libro libro, String estado);
}
