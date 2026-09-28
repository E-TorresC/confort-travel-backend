package com.conforttravel.api.usuario.dto;

import jakarta.validation.constraints.NotNull;

public record CambiarEstadoRequest(

        @NotNull(message = "El estado es obligatorio")
        Boolean activo,

        @NotNull(message = "La versión es obligatoria para actualizar")
        Long version
) {
}