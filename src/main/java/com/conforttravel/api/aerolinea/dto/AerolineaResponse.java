package com.conforttravel.api.aerolinea.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record AerolineaResponse(

        Long id,
        String codigo,
        String nombre,
        Boolean activo,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}