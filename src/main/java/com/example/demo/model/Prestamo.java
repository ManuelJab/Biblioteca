package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// Esta clase representa los prestamos de libros
@Entity
@Table(name = "prestamos")
public class Prestamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relacion con el usuario que solicita el prestamo
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    // Relacion con el libro prestado
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
    
    // Fecha en que se realizo el prestamo
    @Column(nullable = false)
    private LocalDate fechaPrestamo;
    
    // Fecha en que se debe devolver el libro
    @Column(nullable = false)
    private LocalDate fechaDevolucion;
    
    // Estado del prestamo: activo o devuelto
    @Column(nullable = false)
    private String estado;
    
    // Constructores
    public Prestamo() {
    }
    
    public Prestamo(Usuario usuario, Libro libro, LocalDate fechaPrestamo, LocalDate fechaDevolucion, String estado) {
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Libro getLibro() {
        return libro;
    }
    
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
