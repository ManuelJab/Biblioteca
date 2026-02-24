package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

// Este servicio es necesario para que Spring Security pueda autenticar usuarios
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Este metodo se ejecuta cuando un usuario intenta iniciar sesion
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        
        // Convertimos los roles del usuario a authorities de Spring Security
        // Los roles en la DB ya tienen el prefijo ROLE_, no lo agregamos de nuevo
        Set<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol))
                .collect(Collectors.toSet());
        
        // Retornamos un objeto UserDetails que Spring Security puede usar
        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }
}
