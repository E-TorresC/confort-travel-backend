package com.conforttravel.api.checkin.service.impl;

import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.mapper.CheckInMapper;
import com.conforttravel.api.checkin.repository.CheckInRepository;
import com.conforttravel.api.checkin.service.CheckInService;
import com.conforttravel.api.shared.exception.BusinessException;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.tramo.entity.Tramo;
import com.conforttravel.api.tramo.repository.TramoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckInServiceImpl implements CheckInService {

    private final CheckInRepository checkInRepository;
    private final TramoRepository tramoRepository;
    private final CheckInMapper checkInMapper;

    @Override
    @Transactional
    public CheckInResponse crear(CheckInRequest request) {
        Tramo tramo = tramoRepository.findById(request.tramoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tramo no encontrado con id: " + request.tramoId()
                        )
                );

        CheckIn checkIn = checkInMapper.toEntity(request);

        checkIn.setTramo(tramo);
        checkIn.setEstado(EstadoCheckIn.PENDIENTE);

        /*
         * La fecha disponible será calculada por la lógica de negocio
         * correspondiente al tipo de viaje y aerolínea.
         *
         * Esta lógica será desarrollada en la fase de reglas de negocio.
         */
        checkIn.setFechaDisponible(tramo.getFechaHoraSalida());

        CheckIn guardado = checkInRepository.save(checkIn);

        return checkInMapper.toResponseDto(guardado);
    }

    @Override
    public CheckInResponse obtenerPorId(Long id) {
        return checkInMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<CheckInResponse> obtenerTodos() {
        return checkInRepository.findAll()
                .stream()
                .map(checkInMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public CheckInResponse notificar(Long id) {
        CheckIn checkIn = obtenerEntidad(id);

        if (checkIn.getEstado() != EstadoCheckIn.PENDIENTE) {
            throw new BusinessException(
                    "El check-in solo puede pasar de PENDIENTE a NOTIFICADO"
            );
        }

        checkIn.setEstado(EstadoCheckIn.NOTIFICADO);
        checkIn.setFechaNotificacion(OffsetDateTime.now());

        CheckIn actualizado = checkInRepository.save(checkIn);

        return checkInMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public CheckInResponse atender(Long id) {
        CheckIn checkIn = obtenerEntidad(id);

        if (checkIn.getEstado() != EstadoCheckIn.NOTIFICADO) {
            throw new BusinessException(
                    "El check-in solo puede pasar de NOTIFICADO a ATENDIDO"
            );
        }

        checkIn.setEstado(EstadoCheckIn.ATENDIDO);
        checkIn.setFechaAtencion(OffsetDateTime.now());

        CheckIn actualizado = checkInRepository.save(checkIn);

        return checkInMapper.toResponseDto(actualizado);
    }

    private CheckIn obtenerEntidad(Long id) {
        return checkInRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Check-in no encontrado con id: " + id
                        )
                );
    }
}