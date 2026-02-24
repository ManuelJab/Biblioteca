package com.example.demo.model;

import jakarta.persistence.*;

// Esta clase representa la tabla libros en la base de datos
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    // URL de la imagen de portada
    @Column(name = "imagen_url")
    private String imagenUrl;

    // URL del archivo PDF
    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(nullable = false)
    private String autor;

    // ISBN es el codigo unico de cada libro
    @Column(unique = true)
    private String isbn;

    // Cantidad de ejemplares disponibles para prestar
    @Column(nullable = false)
    private Integer ejemplaresDisponibles;

    // Constructores
    public Libro() {
    }

    public Libro(String titulo, String autor, String isbn, Integer ejemplaresDisponibles) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public void setEjemplaresDisponibles(Integer ejemplaresDisponibles) {
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }
}
