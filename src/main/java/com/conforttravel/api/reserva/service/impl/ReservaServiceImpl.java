package com.conforttravel.api.reserva.service.impl;

import com.conforttravel.api.aerolinea.entity.Aerolinea;
import com.conforttravel.api.aerolinea.repository.AerolineaRepository;
import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.reserva.dto.ReservaRequest;
import com.conforttravel.api.reserva.dto.ReservaResponse;
import com.conforttravel.api.reserva.entity.Reserva;
import com.conforttravel.api.reserva.mapper.ReservaMapper;
import com.conforttravel.api.reserva.repository.ReservaRepository;
import com.conforttravel.api.reserva.service.ReservaService;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.tramo.entity.Tramo;
import com.conforttravel.api.tramo.repository.TramoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final TramoRepository tramoRepository;
    private final AerolineaRepository aerolineaRepository;
    private final ReservaMapper reservaMapper;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public ReservaResponse crear(ReservaRequest request) {
        Tramo tramo = obtenerTramo(request.tramoId());
        Aerolinea aerolinea = obtenerAerolinea(request.aerolineaId());

        Reserva reserva = reservaMapper.toEntity(request);
        reserva.setTramo(tramo);
        reserva.setAerolinea(aerolinea);

        Reserva guardada = reservaRepository.save(reserva);

        auditLogService.registrar(
                "CREAR",
                "RESERVA",
                guardada.getId(),
                "Se creó la reserva: " + guardada.getCodigoReserva()
        );

        return reservaMapper.toResponseDto(guardada);
    }

    @Override
    public ReservaResponse obtenerPorId(Long id) {
        return reservaMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<ReservaResponse> obtenerTodos() {
        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ReservaResponse actualizar(
            Long id,
            ReservaRequest request
    ) {
        Reserva reserva = obtenerEntidad(id);

        Tramo tramo = obtenerTramo(request.tramoId());
        Aerolinea aerolinea = obtenerAerolinea(request.aerolineaId());

        reserva.setCodigoReserva(request.codigoReserva());
        reserva.setTramo(tramo);
        reserva.setAerolinea(aerolinea);

        Reserva actualizada = reservaRepository.save(reserva);

        auditLogService.registrar(
                "ACTUALIZAR",
                "RESERVA",
                actualizada.getId(),
                "Se actualizó la reserva: "
                        + actualizada.getCodigoReserva()
        );

        return reservaMapper.toResponseDto(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Reserva reserva = obtenerEntidad(id);

        auditLogService.registrar(
                "ELIMINAR",
                "RESERVA",
                reserva.getId(),
                "Se eliminó la reserva: "
                        + reserva.getCodigoReserva()
        );

        reservaRepository.delete(reserva);
    }

    private Reserva obtenerEntidad(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Reserva no encontrada con id: " + id
                        )
                );
    }

    private Tramo obtenerTramo(Long id) {
        return tramoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tramo no encontrado con id: " + id
                        )
                );
    }

    private Aerolinea obtenerAerolinea(Long id) {
        return aerolineaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aerolínea no encontrada con id: " + id
                        )
                );
    }
}