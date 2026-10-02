package com.conforttravel.api.venta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record VentaResponse(

        Long id,
        String codigo,
        OffsetDateTime fechaCompra,
        BigDecimal precioInicial,
        BigDecimal precioFinal,
        BigDecimal ganancia,
        String observaciones,
        Integer adultos,
        Integer infantes,
        Long clienteId,
        Long agenteId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version
) {
}