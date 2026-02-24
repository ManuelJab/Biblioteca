package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Este repositorio nos permite hacer operaciones en la tabla usuarios
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Metodo para buscar un usuario por su nombre de usuario
    Optional<Usuario> findByUsername(String username);
    
    // Metodo para verificar si existe un usuario con ese username
    boolean existsByUsername(String username);
}
