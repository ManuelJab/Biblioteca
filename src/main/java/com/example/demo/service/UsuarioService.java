package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Este servicio maneja toda la logica relacionada con usuarios
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Metodo para registrar un nuevo usuario
    public Usuario registrarUsuario(String username, String password, String rol) {
        // Verificamos si el usuario ya existe
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Creamos el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        // Encriptamos la contrasena antes de guardarla
        usuario.setPassword(passwordEncoder.encode(password));

        // Asignamos el rol
        Set<String> roles = new HashSet<>();
        roles.add(rol);
        usuario.setRoles(roles);

        // Guardamos en la base de datos
        return usuarioRepository.save(usuario);
    }

    // Metodo para buscar un usuario por username
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Metodo para obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Metodo para obtener usuario por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // Metodo para actualizar usuario
    public void actualizarUsuario(Long id, Usuario usuarioActualizado, String nuevaPassword) {
        Usuario usuarioExistente = obtenerUsuarioPorId(id);

        usuarioExistente.setUsername(usuarioActualizado.getUsername());

        // Solo actualizamos password si no esta vacia
        if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(nuevaPassword));
        }

        // Actualizamos roles
        usuarioExistente.setRoles(usuarioActualizado.getRoles());

        usuarioRepository.save(usuarioExistente);
    }
}
