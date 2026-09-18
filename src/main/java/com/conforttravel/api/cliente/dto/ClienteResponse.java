package com.conforttravel.api.cliente.dto;

import java.time.LocalDateTime;

public record ClienteResponse(

        Long id,

        String nombres,

        String apellidos,

        String telefono,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}