package com.conforttravel.api.usuario.repository;

import com.conforttravel.api.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}