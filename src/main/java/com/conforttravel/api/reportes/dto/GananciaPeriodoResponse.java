package com.conforttravel.api.reportes.dto;

import java.math.BigDecimal;

public record GananciaPeriodoResponse(

        Long cantidadVentas,

        BigDecimal montoTotalVendido,

        BigDecimal costoTotal,

        BigDecimal gananciaTotal

) {
}