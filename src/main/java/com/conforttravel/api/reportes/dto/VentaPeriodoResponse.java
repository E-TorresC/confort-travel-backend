package com.conforttravel.api.reportes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VentaPeriodoResponse(

        Long ventaId,

        LocalDateTime fechaCompra,

        String cliente,

        String agente,

        BigDecimal precioInicial,

        BigDecimal precioFinal,

        BigDecimal ganancia

) {
}