package com.conforttravel.api.usuario.dto;

import com.conforttravel.api.usuario.entity.Rol;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record UsuarioResponse(

        Long id,

        String nombre,

        String apellido,

        String email,

        Rol rol,

        Boolean activo,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt,

        Long version
) {
}