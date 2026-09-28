package com.conforttravel.api.reportes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record VentaPeriodoResponse(
        Long ventaId,
        OffsetDateTime fechaCompra,
        String cliente,
        String agente,
        BigDecimal precioInicial,
        BigDecimal precioFinal
) {
    // Propiedad calculada, no forma parte del constructor
    @JsonProperty("ganancia")
    public BigDecimal ganancia() {
        if (precioInicial == null || precioFinal == null) {
            return null;
        }
        return precioFinal.subtract(precioInicial);
    }
}