package com.conforttravel.api.venta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VentaResponse(

        Long id,

        LocalDateTime fechaCompra,

        BigDecimal precioInicial,

        BigDecimal precioFinal,

        BigDecimal ganancia,

        String observaciones,

        Integer adultos,

        Integer infantes,

        Long clienteId,

        Long agenteId,

        Long viajeId,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        Long version
) {
}