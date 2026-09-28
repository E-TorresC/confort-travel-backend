package com.conforttravel.api.dashboard.service.impl;

import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.repository.CheckInRepository;
import com.conforttravel.api.dashboard.dto.DashboardResumenResponse;
import com.conforttravel.api.dashboard.dto.ProximoViajeResponse;
import com.conforttravel.api.dashboard.service.DashboardService;
import com.conforttravel.api.tramo.repository.TramoRepository;
import com.conforttravel.api.venta.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final VentaRepository ventaRepository;
    private final CheckInRepository checkInRepository;
    private final TramoRepository tramoRepository;

    @Override
    public DashboardResumenResponse obtenerResumen() {
        Long cantidadVentas = ventaRepository.count();

        // Manejamos posibles valores nulos si aún no hay ventas registradas
        BigDecimal montoTotalVendido = ventaRepository.sumMontoTotalVendido();
        if (montoTotalVendido == null) montoTotalVendido = BigDecimal.ZERO;

        BigDecimal gananciaAcumulada = ventaRepository.sumGananciaAcumulada();
        if (gananciaAcumulada == null) gananciaAcumulada = BigDecimal.ZERO;

        long checkInsPendientes = checkInRepository.countByEstado(EstadoCheckIn.PENDIENTE);
        long checkInsNotificados = checkInRepository.countByEstado(EstadoCheckIn.NOTIFICADO);
        long checkInsAtendidos = checkInRepository.countByEstado(EstadoCheckIn.ATENDIDO);

        return new DashboardResumenResponse(
                cantidadVentas,
                montoTotalVendido,
                gananciaAcumulada,
                checkInsPendientes,
                checkInsNotificados,
                checkInsAtendidos
        );
    }

    @Override
    public List<ProximoViajeResponse> obtenerProximosViajes() {
        // Trae los 5 vuelos/tramos más próximos a partir de este momento
        return tramoRepository.findTop5ByFechaHoraSalidaGreaterThanEqualOrderByFechaHoraSalidaAsc(
                OffsetDateTime.now(ZoneOffset.UTC)
        ).stream().map(tramo -> new ProximoViajeResponse(
                tramo.getId(),
                tramo.getOrigen(),
                tramo.getDestino(),
                tramo.getFechaHoraSalida()
        )).toList();
    }
}