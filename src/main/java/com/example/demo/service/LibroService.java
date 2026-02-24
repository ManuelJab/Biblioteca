package com.example.demo.service;

import com.example.demo.model.Libro;
import com.example.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

// Este servicio maneja toda la logica relacionada con libros
@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    // Metodo para agregar un nuevo libro al catalogo
    public Libro agregarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    // Metodo para obtener todos los libros
    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    // Metodo para buscar un libro por su ID
    public Libro buscarLibroPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    // Metodo para buscar libros por titulo
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Metodo para buscar libros por autor
    public List<Libro> buscarPorAutor(String autor) {
        return libroRepository.findByAutorContainingIgnoreCase(autor);
    }

    // Metodo para actualizar un libro
    public Libro actualizarLibro(Long id, Libro libroActualizado) {
        Libro libro = buscarLibroPorId(id);

        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setIsbn(libroActualizado.getIsbn());
        libro.setEjemplaresDisponibles(libroActualizado.getEjemplaresDisponibles());

        // Actualizar Imagen: Si viene nueva, borrar anterior y setear nueva
        if (libroActualizado.getImagenUrl() != null && !libroActualizado.getImagenUrl().isEmpty()) {
            eliminarArchivo(libro.getImagenUrl());
            libro.setImagenUrl(libroActualizado.getImagenUrl());
        }

        // Actualizar PDF: Si viene nuevo, borrar anterior y setear nuevo
        if (libroActualizado.getPdfUrl() != null && !libroActualizado.getPdfUrl().isEmpty()) {
            eliminarArchivo(libro.getPdfUrl());
            libro.setPdfUrl(libroActualizado.getPdfUrl());
        }

        return libroRepository.save(libro);
    }

    // Metodo para eliminar un libro
    public void eliminarLibro(Long id) {
        Libro libro = buscarLibroPorId(id);

        // Eliminar archivos asociados
        eliminarArchivo(libro.getImagenUrl());
        eliminarArchivo(libro.getPdfUrl());

        libroRepository.deleteById(id);
    }

    // Metodo para guardar un archivo subido
    public String guardarArchivo(MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        // Usar carpeta "uploads" en la raíz del proyecto
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();

        // Crear directorio si no existe
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generar nombre unico y limpiar el nombre original
        String originalFilename = archivo.getOriginalFilename();
        String safeFilename = UUID.randomUUID().toString() + "_"
                + (originalFilename != null ? originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_") : "file");
        Path filePath = uploadPath.resolve(safeFilename);

        // Guardar archivo
        Files.copy(archivo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retornar la URL relativa para acceso web (configurada en WebConfig)
        return "/uploads/" + safeFilename;
    }

    // Metodo auxiliar para eliminar archivo
    private void eliminarArchivo(String url) {
        if (url == null || url.isEmpty()) {
            return;
        }

        try {
            // Extraer nombre del archivo de la URL
            if (url.startsWith("/uploads/")) {
                String filename = url.substring("/uploads/".length());
                Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
                Path filePath = uploadPath.resolve(filename);
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo simple de errores
        }
    }
}
