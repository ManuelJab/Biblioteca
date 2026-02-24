package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Esta clase representa la tabla usuarios en la base de datos
@Entity
@Table(name = "usuarios")
public class Usuario {

    // El id es la clave primaria y se genera automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El username debe ser unico para cada usuario
    @Column(unique = true, nullable = false)
    private String username;

    // La contrasena se guardara encriptada
    @Column(nullable = false)
    private String password;

    // Rol del usuario (ROLE_USER o ROLE_ADMIN)
    @Column(nullable = false)
    private String rol = "ROLE_USER";

    // Constructores
    public Usuario() {
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // Metodo de compatibilidad para mantener la interfaz anterior
    public Set<String> getRoles() {
        Set<String> roles = new HashSet<>();
        if (rol != null && !rol.isEmpty()) {
            roles.add(rol);
        }
        return roles;
    }

    public void setRoles(Set<String> roles) {
        if (roles != null && !roles.isEmpty()) {
            this.rol = roles.iterator().next();
        }
    }
}
