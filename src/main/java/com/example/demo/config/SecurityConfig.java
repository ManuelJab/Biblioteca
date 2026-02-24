package com.example.demo.config;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Esta clase configura la seguridad de la aplicacion
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Bean para encriptar contrasenas
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuracion de seguridad HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configuramos las autorizaciones
                .authorizeHttpRequests(auth -> auth
                        // Permitimos acceso publico a recursos estaticos (CSS, JS, imagenes)
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                        // Permitimos acceso publico a la pagina de registro y login
                        .requestMatchers("/registro", "/login").permitAll()
                        // Solo los ADMIN pueden acceder a estas rutas
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Los usuarios normales pueden acceder a estas rutas
                        .requestMatchers("/usuario/**").hasRole("USER")
                        // Cualquier otra peticion requiere autenticacion
                        .anyRequest().authenticated())
                // Configuramos el formulario de login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

    // Configuracion del AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}
