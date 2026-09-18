package com.conforttravel.api.aerolinea.dto;

import java.time.LocalDateTime;

public record AerolineaResponse(

        Long id,

        String nombre,

        String codigo,

        Boolean activo,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}