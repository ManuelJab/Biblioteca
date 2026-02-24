package com.example.demo;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

	/*
	 * @Bean
	 * public CommandLineRunner fixRoles(UsuarioRepository usuarioRepository) {
	 * return args -> {
	 * List<Usuario> usuarios = usuarioRepository.findAll();
	 * for (Usuario usuario : usuarios) {
	 * Set<String> roles = usuario.getRoles();
	 * boolean modified = false;
	 * Set<String> newRoles = new HashSet<>();
	 * 
	 * for (String rol : roles) {
	 * if (rol.equals("USER")) {
	 * newRoles.add("ROLE_USER");
	 * modified = true;
	 * } else if (rol.equals("ADMIN")) {
	 * newRoles.add("ROLE_ADMIN");
	 * modified = true;
	 * } else {
	 * newRoles.add(rol);
	 * }
	 * }
	 * 
	 * if (modified) {
	 * usuario.setRoles(newRoles);
	 * usuarioRepository.save(usuario);
	 * System.out.println("Roles corregidos para el usuario: " +
	 * usuario.getUsername());
	 * }
	 * }
	 * };
	 * }
	 */

}
