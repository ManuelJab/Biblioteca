package com.example.demo.service;

import com.example.demo.model.Libro;
import com.example.demo.model.Prestamo;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

// Este servicio maneja toda la logica relacionada con prestamos
@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroService libroService;

    // Metodo para solicitar un prestamo de libro
    @Transactional
    public Prestamo solicitarPrestamo(Usuario usuario, Long libroId) {
        // Buscamos el libro
        Libro libro = libroService.buscarLibroPorId(libroId);

        // Verificamos si hay ejemplares disponibles
        if (libro.getEjemplaresDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles");
        }

        // Verificar si el usuario ya tiene este libro prestado
        if (prestamoRepository.existsByUsuarioAndLibroAndEstado(usuario, libro, "ACTIVO")) {
            throw new RuntimeException("Ya tienes un préstamo activo para este libro");
        }

        // Creamos el prestamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        // El prestamo dura 15 dias
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(15));
        prestamo.setEstado("ACTIVO");

        // Reducimos la cantidad de ejemplares disponibles
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroService.agregarLibro(libro);

        // Guardamos el prestamo
        return prestamoRepository.save(prestamo);
    }

    // Metodo para obtener los prestamos de un usuario
    public List<Prestamo> obtenerPrestamosPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuario(usuario);
    }

    // Metodo para obtener los prestamos activos de un usuario
    public List<Prestamo> obtenerPrestamosActivosPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuarioAndEstado(usuario, "ACTIVO");
    }

    // Metodo para obtener todos los prestamos activos (para admin)
    public List<Prestamo> obtenerTodosPrestamosActivos() {
        return prestamoRepository.findByEstado("ACTIVO");
    }

    // Metodo para devolver un libro
    @Transactional
    public void devolverLibro(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));

        if ("DEVUELTO".equals(prestamo.getEstado())) {
            throw new RuntimeException("Este prestamo ya ha sido devuelto");
        }

        // Cambiamos el estado del prestamo
        prestamo.setEstado("DEVUELTO");
        prestamoRepository.save(prestamo);

        // Aumentamos la cantidad de ejemplares disponibles
        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroService.agregarLibro(libro);
    }
}
