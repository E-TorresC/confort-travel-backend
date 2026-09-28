package com.conforttravel.api.dashboard.dto;

import java.math.BigDecimal;

public record DashboardResumenResponse(
        Long cantidadVentas,
        BigDecimal montoTotalVendido,
        BigDecimal gananciaAcumulada,
        Long checkInsPendientes,
        Long checkInsNotificados,
        Long checkInsAtendidos
) {
}