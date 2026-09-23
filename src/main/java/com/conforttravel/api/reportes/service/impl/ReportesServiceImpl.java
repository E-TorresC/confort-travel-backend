package com.conforttravel.api.reportes.service.impl;

import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.reportes.dto.CheckInReporteResponse;
import com.conforttravel.api.reportes.dto.GananciaPeriodoResponse;
import com.conforttravel.api.reportes.dto.ReporteAgrupadoResponse;
import com.conforttravel.api.reportes.dto.VentaPeriodoResponse;
import com.conforttravel.api.reportes.repository.ReportesRepository;
import com.conforttravel.api.reportes.service.ReportesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportesServiceImpl implements ReportesService {

    private final ReportesRepository reportesRepository;


    @Override
    public List<VentaPeriodoResponse> ventasPorPeriodo(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    ) {

        validarRangoFechas(fechaDesde, fechaHasta);

        return reportesRepository.obtenerVentasPorPeriodo(
                inicioDelDia(fechaDesde),
                inicioDelDiaSiguiente(fechaHasta)
        );
    }


    @Override
    public List<ReporteAgrupadoResponse> ventasPorAgente(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    ) {

        validarRangoFechas(fechaDesde, fechaHasta);

        return reportesRepository.obtenerVentasPorAgente(
                inicioDelDia(fechaDesde),
                inicioDelDiaSiguiente(fechaHasta)
        );
    }


    @Override
    public List<ReporteAgrupadoResponse> ventasPorAerolinea(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    ) {

        validarRangoFechas(fechaDesde, fechaHasta);

        List<Object[]> resultados =
                reportesRepository.obtenerVentasPorAerolinea(
                        inicioDelDia(fechaDesde),
                        inicioDelDiaSiguiente(fechaHasta)
                );

        return resultados.stream()
                .map(this::mapearReporteAerolinea)
                .toList();
    }


    @Override
    public GananciaPeriodoResponse gananciaPorPeriodo(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    ) {

        validarRangoFechas(fechaDesde, fechaHasta);

        return reportesRepository.obtenerGananciaPorPeriodo(
                inicioDelDia(fechaDesde),
                inicioDelDiaSiguiente(fechaHasta)
        );
    }


    @Override
    public List<CheckInReporteResponse> checkInsPorEstado(
            EstadoCheckIn estado
    ) {

        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado del check-in es obligatorio"
            );
        }

        return reportesRepository.obtenerCheckInsPorEstado(estado);
    }


    private ReporteAgrupadoResponse mapearReporteAerolinea(
            Object[] fila
    ) {

        Long id = ((Number) fila[0]).longValue();

        String nombre = (String) fila[1];

        Long cantidadVentas =
                ((Number) fila[2]).longValue();

        BigDecimal montoTotal =
                convertirBigDecimal(fila[3]);

        BigDecimal gananciaTotal =
                convertirBigDecimal(fila[4]);

        return new ReporteAgrupadoResponse(
                id,
                nombre,
                cantidadVentas,
                montoTotal,
                gananciaTotal
        );
    }


    private BigDecimal convertirBigDecimal(Object valor) {

        if (valor == null) {
            return BigDecimal.ZERO;
        }

        if (valor instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }

        return new BigDecimal(valor.toString());
    }


    private void validarRangoFechas(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    ) {

        if (fechaDesde == null || fechaHasta == null) {
            throw new IllegalArgumentException(
                    "Las fechas desde y hasta son obligatorias"
            );
        }

        if (fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException(
                    "La fecha desde no puede ser posterior a la fecha hasta"
            );
        }
    }

    private OffsetDateTime inicioDelDia(LocalDate fecha) {
        return fecha.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
    }

    private OffsetDateTime inicioDelDiaSiguiente(LocalDate fecha) {
        return fecha.plusDays(1).atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
    }
}