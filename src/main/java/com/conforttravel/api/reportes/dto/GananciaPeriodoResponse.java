package com.conforttravel.api.reportes.dto;

import java.math.BigDecimal;

public record GananciaPeriodoResponse(
        Long cantidadVentas,
        BigDecimal montoTotalVendido,
        BigDecimal costoTotal,
        BigDecimal gananciaTotal
) {
    public GananciaPeriodoResponse(
            Long cantidadVentas,
            Number montoTotalVendido,
            Number costoTotal,
            Number gananciaTotal
    ) {
        this(
                cantidadVentas,
                montoTotalVendido == null ? BigDecimal.ZERO : new BigDecimal(montoTotalVendido.toString()),
                costoTotal == null ? BigDecimal.ZERO : new BigDecimal(costoTotal.toString()),
                gananciaTotal == null ? BigDecimal.ZERO : new BigDecimal(gananciaTotal.toString())
        );
    }
}