package com.example.demo.controller;

import com.example.demo.model.Libro;
import com.example.demo.service.LibroService;
import com.example.demo.service.PrestamoService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

// Este controlador maneja las funciones de administrador
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private UsuarioService usuarioService;

    // Muestra el panel de administrador
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("libros", libroService.obtenerTodosLosLibros());
        model.addAttribute("prestamos", prestamoService.obtenerTodosPrestamosActivos());
        return "admin/dashboard";
    }

    // Muestra el formulario para agregar un libro
    @GetMapping("/libros/nuevo")
    public String nuevoLibroForm() {
        return "admin/libro-form";
    }

    // Procesa el formulario para agregar un libro
    @PostMapping("/libros/nuevo")
    public String agregarLibro(@ModelAttribute Libro libro,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            @RequestParam("pdfFile") MultipartFile pdfFile) {
        try {
            if (!imagenFile.isEmpty()) {
                libro.setImagenUrl(libroService.guardarArchivo(imagenFile));
            }
            if (!pdfFile.isEmpty()) {
                libro.setPdfUrl(libroService.guardarArchivo(pdfFile));
            }
            libroService.agregarLibro(libro);
            return "redirect:/admin/dashboard?libroCreado";
        } catch (IOException e) {
            return "redirect:/admin/dashboard?error=ErrorSubida";
        }
    }

    // Muestra el formulario para editar un libro
    @GetMapping("/libros/editar/{id}")
    public String editarLibroForm(@PathVariable Long id, Model model) {
        model.addAttribute("libro", libroService.buscarLibroPorId(id));
        return "admin/libro-editar";
    }

    // Procesa el formulario de edicion de libro
    @PostMapping("/libros/editar/{id}")
    public String actualizarLibro(@PathVariable Long id,
            @ModelAttribute Libro libro,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            @RequestParam("pdfFile") MultipartFile pdfFile) {
        try {
            // Si se suben nuevos archivos, se guardan. Si no, el servicio mantendra los
            // anteriores (si asi lo configuramos)
            // Pero espera, el servicio 'actualizarLibro' sobrescribe todo.
            // Necesitamos pasarle al servicio las URLs nuevas solo si existen.

            // Logica mejorada:
            // 1. Obtener libro existente para preservar URLs si no cambiaron?
            // No, el controller debe decidir que URL pasar.

            // O mejor: Si el archivo viene vacio, seteamos null en el objeto 'libro' que
            // viene del form?
            // El problema es que el @ModelAttribute 'libro' solo tiene los campos del form.
            // Si el form no envia las URLs viejas, estaran null.
            // Si el usuario NO sube archivo, queremos conservar el anterior.

            // Vamos a hacerlo asi:
            // Seteamos las URLs nuevas en el objeto libro si hay archivo.
            // Si NO hay archivo, dejamos las URLs en null (o como vengan).
            // EN EL SERVICIO (LibroService.actualizarLibro), verificamos:
            // Si la nueva URL es null, NO sobrescribimos la vieja.

            if (!imagenFile.isEmpty()) {
                libro.setImagenUrl(libroService.guardarArchivo(imagenFile));
            }
            if (!pdfFile.isEmpty()) {
                libro.setPdfUrl(libroService.guardarArchivo(pdfFile));
            }

            // NOTA: Para que esto funcione, en LibroService.actualizarLibro debemos
            // chequear si es null antes de setear.
            // Voy a asumir que modificare LibroService para eso, o lo hago aqui.
            // Hagamoslo seguro: recuperar el libro original aqui no cuesta nada pero el
            // servicio ya lo busca.
            // Mejor modifiquemos LibroService.actualizarLibro para que sea inteligente con
            // los nulls.

            libroService.actualizarLibro(id, libro);
            return "redirect:/admin/dashboard?libroActualizado";
        } catch (IOException e) {
            return "redirect:/admin/dashboard?error=ErrorSubida";
        }
    }

    // Elimina un libro
    @GetMapping("/libros/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return "redirect:/admin/dashboard?libroEliminado";
    }

    // Muestra la lista de usuarios con prestamos activos
    @GetMapping("/usuarios-prestamos")
    public String usuariosConPrestamos(Model model) {
        model.addAttribute("usuarios", usuarioService.obtenerTodosLosUsuarios());
        model.addAttribute("prestamos", prestamoService.obtenerTodosPrestamosActivos());
        return "admin/usuarios-prestamos";
    }

    // Procesa la devolucion de un libro
    @PostMapping("/prestamos/devolver/{id}")
    public String devolverLibro(@PathVariable Long id) {
        prestamoService.devolverLibro(id);
        return "redirect:/admin/dashboard?libroDevuelto";
    }

    // Muestra el formulario para editar un usuario
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerUsuarioPorId(id));
        return "admin/usuario-editar";
    }

    // Procesa el formulario de edicion de usuario
    @PostMapping("/usuarios/editar/{id}")
    public String actualizarUsuario(@PathVariable Long id,
            @ModelAttribute com.example.demo.model.Usuario usuario,
            @RequestParam(required = false) String nuevaPassword,
            @RequestParam(required = false) String rol) {

        // Manejamos el rol manualmente ya que viene como String simple del form
        if (rol != null && !rol.isEmpty()) {
            java.util.Set<String> roles = new java.util.HashSet<>();
            roles.add(rol);
            usuario.setRoles(roles);
        }

        usuarioService.actualizarUsuario(id, usuario, nuevaPassword);
        return "redirect:/admin/usuarios-prestamos?usuarioActualizado";
    }
}
