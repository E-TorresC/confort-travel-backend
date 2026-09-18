package com.conforttravel.api.usuario.dto;

import com.conforttravel.api.usuario.entity.Rol;

import java.time.LocalDateTime;

public record UsuarioResponse(

        Long id,

        String nombre,

        String apellido,

        String email,

        Rol rol,

        Boolean activo,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        Long version
) {
}