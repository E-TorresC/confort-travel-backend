package com.conforttravel.api.viaje.dto;

import jakarta.validation.constraints.NotNull;

public record ViajeRequest(

        @NotNull(message = "La venta es obligatoria")
        Long ventaId
) {
}