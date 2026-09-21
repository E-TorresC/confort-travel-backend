package com.conforttravel.api.venta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VentaFilterRequest(

        LocalDateTime fechaDesde,

        LocalDateTime fechaHasta,

        BigDecimal precioMinimo,

        BigDecimal precioMaximo,

        Long clienteId,

        Long agenteId

) {
}