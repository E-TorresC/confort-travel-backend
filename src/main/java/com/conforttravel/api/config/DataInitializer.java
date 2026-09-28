package com.conforttravel.api.config;

import com.conforttravel.api.usuario.entity.Rol;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // Restringido exclusivamente al perfil de desarrollo
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Se inyectan las variables de entorno, con un valor por defecto para no romper el entorno local
    @Value("${INITIAL_ADMIN_EMAIL:admin@conforttravel.com}")
    private String adminEmail;

    @Value("${INITIAL_ADMIN_PASSWORD:Admin123*}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        // 1. Verificamos si ya existe un administrador
        boolean adminExiste = usuarioRepository.findByEmail(adminEmail).isPresent();

        if (adminExiste) {
            log.info("El usuario administrador ya existe. No se crea uno nuevo.");
            return;
        }

        // 2. Si no existe, lo creamos
        log.info("No se encontró usuario administrador. Creando uno por defecto...");

        Usuario admin = new Usuario();
        admin.setNombre("Admin");
        admin.setApellido("ConfortTravel");
        admin.setEmail(adminEmail);
        admin.setPasswordHash(passwordEncoder.encode(adminPassword));
        admin.setRol(Rol.ADMIN);
        admin.setActivo(true);

        usuarioRepository.save(admin);

        log.info("Usuario administrador creado exitosamente con email: {}", adminEmail);
    }
}