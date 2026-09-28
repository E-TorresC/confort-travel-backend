package com.conforttravel.api.venta.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record VentaRequest(

        @NotNull(message = "La fecha de compra es obligatoria")
        OffsetDateTime fechaCompra,

        @NotNull(message = "El precio inicial es obligatorio")
        @DecimalMin(
                value = "0.00",
                message = "El precio inicial no puede ser negativo"
        )
        BigDecimal precioInicial,

        @NotNull(message = "El precio final es obligatorio")
        @DecimalMin(
                value = "0.00",
                message = "El precio final no puede ser negativo"
        )
        BigDecimal precioFinal,

        @Size(
                max = 1000,
                message = "Las observaciones no pueden superar los 1000 caracteres"
        )
        String observaciones,

        @NotNull(message = "La cantidad de adultos es obligatoria")
        @Min(
                value = 0,
                message = "La cantidad de adultos no puede ser negativa"
        )
        Integer adultos,

        @NotNull(message = "La cantidad de infantes es obligatoria")
        @Min(
                value = 0,
                message = "La cantidad de infantes no puede ser negativa"
        )
        Integer infantes,

        @NotNull(message = "El cliente es obligatorio")
        Long clienteId,

        @NotNull(message = "El agente es obligatorio")
        Long agenteId,

        Long version
) {
}