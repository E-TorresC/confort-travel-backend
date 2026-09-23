package com.conforttravel.api.venta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record VentaFilterRequest(

        OffsetDateTime fechaDesde,

        OffsetDateTime fechaHasta,

        BigDecimal precioMinimo,

        BigDecimal precioMaximo,

        Long clienteId,

        Long agenteId

) {
}