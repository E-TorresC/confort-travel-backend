package com.conforttravel.api.usuario.repository;

import com.conforttravel.api.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Aquí declaras el método que falta
    Optional<Usuario> findByEmail(String email);
}