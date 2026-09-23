package com.conforttravel.api.servicio.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record ServicioAdicionalResponse(

        Long id,

        String nombre,

        String descripcion,

        Boolean activo,

        OffsetDateTime createdAt,

        OffsetDateTime updatedAt
) {
}