package com.example.demo.controller;

import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Este controlador maneja el registro y login de usuarios
@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Muestra la pagina de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Muestra la pagina de registro
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    // Procesa el formulario de registro
    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        try {
            // Registramos el nuevo usuario siempre como ROLE_USER
            // Los administradores se crean manualmente en la base de datos
            usuarioService.registrarUsuario(username, password, "ROLE_USER");
            // Redirigimos al login con mensaje de exito
            return "redirect:/login?registroExitoso";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}
