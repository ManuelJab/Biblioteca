package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Este controlador maneja la pagina principal
@Controller
public class HomeController {
    
    // Redirige a la pagina correspondiente segun el rol del usuario
    @GetMapping("/home")
    public String home(Authentication authentication) {
        // Si el usuario es ADMIN, lo enviamos al panel de admin
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        }
        // Si es USER, lo enviamos al catalogo
        return "redirect:/usuario/catalogo";
    }
}
