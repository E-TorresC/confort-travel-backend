package com.conforttravel.api.venta.dto;

import jakarta.validation.constraints.NotNull;

public record VentaServicioRequest(

        @NotNull(message = "La venta es obligatoria")
        Long ventaId,

        @NotNull(message = "El servicio adicional es obligatorio")
        Long servicioAdicionalId
) {
}