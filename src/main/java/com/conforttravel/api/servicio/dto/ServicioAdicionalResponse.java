package com.conforttravel.api.servicio.dto;

import java.time.LocalDateTime;

public record ServicioAdicionalResponse(

        Long id,

        String nombre,

        String descripcion,

        Boolean activo,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}