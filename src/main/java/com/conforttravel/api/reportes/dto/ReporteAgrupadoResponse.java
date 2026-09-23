package com.conforttravel.api.reportes.dto;

import java.math.BigDecimal;

public record ReporteAgrupadoResponse(

        Long id,

        String nombre,

        Long cantidadVentas,

        BigDecimal montoTotal,

        BigDecimal gananciaTotal

) {
}