package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.LibroService;
import com.example.demo.service.PrestamoService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

// Este controlador maneja las funciones de usuario normal
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private UsuarioService usuarioService;

    // Muestra el catalogo de libros
    @GetMapping("/catalogo")
    public String catalogo(Model model, Authentication authentication) {
        model.addAttribute("libros", libroService.obtenerTodosLosLibros());

        if (authentication != null) {
            Usuario usuario = usuarioService.buscarPorUsername(authentication.getName());
            List<Long> prestamosIds = prestamoService.obtenerPrestamosActivosPorUsuario(usuario)
                    .stream()
                    .map(prestamo -> prestamo.getLibro().getId())
                    .collect(Collectors.toList());
            model.addAttribute("prestamosIds", prestamosIds);
        }

        return "usuario/catalogo";
    }

    // Busca libros por titulo o autor
    @GetMapping("/buscar")
    public String buscarLibros(@RequestParam String termino, Model model) {
        // Buscamos por titulo y por autor
        model.addAttribute("libros", libroService.buscarPorTitulo(termino));
        return "usuario/catalogo";
    }

    // Solicita un prestamo de libro
    @PostMapping("/solicitar-prestamo/{libroId}")
    public String solicitarPrestamo(@PathVariable Long libroId, Authentication authentication) {
        // Obtenemos el usuario actual
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName());

        try {
            // Solicitamos el prestamo
            prestamoService.solicitarPrestamo(usuario, libroId);
            return "redirect:/usuario/mis-prestamos?prestamoSolicitado";
        } catch (Exception e) {
            return "redirect:/usuario/catalogo?error=" + e.getMessage();
        }
    }

    // Muestra los prestamos del usuario
    @GetMapping("/mis-prestamos")
    public String misPrestamos(Authentication authentication, Model model) {
        // Obtenemos el usuario actual
        Usuario usuario = usuarioService.buscarPorUsername(authentication.getName());

        // Obtenemos sus prestamos activos
        model.addAttribute("prestamos", prestamoService.obtenerPrestamosActivosPorUsuario(usuario));
        return "usuario/mis-prestamos";
    }

    // Devuelve un libro (usuario)
    @PostMapping("/prestamos/devolver/{id}")
    public String devolverLibro(@PathVariable Long id, Authentication authentication) {
        // Verificar que el prestamo pertenece al usuario (seguridad basica)
        // Por simplicidad, confiamos en el ID, pero idealmente se verificaria
        prestamoService.devolverLibro(id);
        return "redirect:/usuario/mis-prestamos?libroDevuelto";
    }

    // Leer libro online
    @GetMapping("/leer/{id}")
    public String leerLibro(@PathVariable Long id, Model model) {
        // En una app real, verificariamos que el usuario tiene un prestamo activo de
        // este libro
        model.addAttribute("libro", libroService.buscarLibroPorId(id));
        return "usuario/leer-libro";
    }
}
