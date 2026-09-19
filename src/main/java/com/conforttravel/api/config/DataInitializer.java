package com.conforttravel.api.config;

import com.conforttravel.api.usuario.entity.Rol;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 1. Verificamos si ya existe un administrador
        boolean adminExiste = usuarioRepository.findByEmail("admin@conforttravel.com").isPresent();

        if (adminExiste) {
            log.info("El usuario administrador ya existe. No se crea uno nuevo.");
            return;
        }

        // 2. Si no existe, lo creamos
        log.info("No se encontró usuario administrador. Creando uno por defecto...");

        Usuario admin = new Usuario();
        admin.setNombre("Admin");
        admin.setApellido("ConfortTravel");
        admin.setEmail("admin@conforttravel.com");
        admin.setPasswordHash(passwordEncoder.encode("Admin123*")); // <-- Cambia esto en producción
        admin.setRol(Rol.ADMIN); // Asumo que tienes un enum Rol con ADMIN
        admin.setActivo(true);

        usuarioRepository.save(admin);

        log.info("Usuario administrador creado exitosamente con email: admin@conforttravel.com");
    }
}