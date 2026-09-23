package com.conforttravel.api.cliente.dto;

import java.time.OffsetDateTime;

public record ClienteResponse(

        Long id,

        String nombres,

        String apellidos,

        String telefono,

        OffsetDateTime createdAt,

        OffsetDateTime  updatedAt
) {
}