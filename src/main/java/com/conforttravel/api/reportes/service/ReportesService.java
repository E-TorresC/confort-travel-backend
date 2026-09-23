package com.conforttravel.api.reportes.service;

import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.reportes.dto.CheckInReporteResponse;
import com.conforttravel.api.reportes.dto.GananciaPeriodoResponse;
import com.conforttravel.api.reportes.dto.ReporteAgrupadoResponse;
import com.conforttravel.api.reportes.dto.VentaPeriodoResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReportesService {

    List<VentaPeriodoResponse> ventasPorPeriodo(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    );

    List<ReporteAgrupadoResponse> ventasPorAgente(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    );

    List<ReporteAgrupadoResponse> ventasPorAerolinea(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    );

    GananciaPeriodoResponse gananciaPorPeriodo(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    );

    List<CheckInReporteResponse> checkInsPorEstado(
            EstadoCheckIn estado
    );
}