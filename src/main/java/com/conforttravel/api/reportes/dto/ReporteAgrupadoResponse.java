package com.conforttravel.api.reportes.dto;

import java.math.BigDecimal;

public record ReporteAgrupadoResponse(
        Long id,
        String nombre,
        Long cantidadVentas,
        BigDecimal montoTotal,
        BigDecimal gananciaTotal
) {
    public ReporteAgrupadoResponse(
            Long id,
            String nombre,
            Long cantidadVentas,
            Number montoTotal,
            Number gananciaTotal
    ) {
        this(
                id,
                nombre,
                cantidadVentas,
                montoTotal == null ? BigDecimal.ZERO : new BigDecimal(montoTotal.toString()),
                gananciaTotal == null ? BigDecimal.ZERO : new BigDecimal(gananciaTotal.toString())
        );
    }
}