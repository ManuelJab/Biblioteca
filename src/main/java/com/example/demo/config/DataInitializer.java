package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Esta clase ya no crea datos automaticamente
// Los libros y usuarios deben estar en la base de datos MySQL
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        // No se crean datos automaticamente
        // Usa los archivos SQL para insertar usuarios y libros
        System.out.println("Aplicacion iniciada - Los datos deben estar en la base de datos");
    }
}
