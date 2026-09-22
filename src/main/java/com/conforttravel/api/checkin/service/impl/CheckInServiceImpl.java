package com.conforttravel.api.checkin.service.impl;

import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.mapper.CheckInMapper;
import com.conforttravel.api.checkin.repository.CheckInRepository;
import com.conforttravel.api.checkin.service.CheckInService;
import com.conforttravel.api.shared.exception.ConflictException;
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
@Transactional
public class CheckInServiceImpl implements CheckInService {

    private final CheckInRepository checkInRepository;
    private final TramoRepository tramoRepository;
    private final CheckInMapper checkInMapper;

    @Override
    public CheckInResponse crear(CheckInRequest request) {

        Tramo tramo = tramoRepository.findById(request.tramoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tramo no encontrado con id: "
                                        + request.tramoId()
                        )
                );

        if (checkInRepository.existsByTramoId(request.tramoId())) {

            throw new ConflictException(
                    "El tramo ya tiene un seguimiento de check-in"
            );
        }

        OffsetDateTime fechaDisponible =
                calcularFechaDisponible(tramo);

        CheckIn checkIn = CheckIn.builder()
                .tramo(tramo)
                .estado(EstadoCheckIn.PENDIENTE)
                .fechaDisponible(fechaDisponible)
                .build();

        return checkInMapper.toResponseDto(
                checkInRepository.save(checkIn)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CheckInResponse obtenerPorId(Long id) {

        CheckIn checkIn = obtenerEntidad(id);

        return checkInMapper.toResponseDto(checkIn);
    }

    @Override
    @Transactional(readOnly = true)
    public CheckInResponse obtenerPorTramo(Long tramoId) {

        CheckIn checkIn = checkInRepository
                .findByTramoId(tramoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe seguimiento de check-in " +
                                        "para el tramo con id: " + tramoId
                        )
                );

        return checkInMapper.toResponseDto(checkIn);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CheckInResponse> obtenerTodos() {

        return checkInRepository.findAll()
                .stream()
                .map(checkInMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CheckInResponse> obtenerPorEstado(
            EstadoCheckIn estado
    ) {

        return checkInRepository.findByEstado(estado)
                .stream()
                .map(checkInMapper::toResponseDto)
                .toList();
    }

    @Override
    public CheckInResponse marcarNotificado(Long id) {

        CheckIn checkIn = obtenerEntidad(id);

        if (checkIn.getEstado() != EstadoCheckIn.PENDIENTE) {

            throw new ConflictException(
                    "El check-in no se encuentra en estado PENDIENTE"
            );
        }

        checkIn.setEstado(EstadoCheckIn.NOTIFICADO);
        checkIn.setFechaNotificacion(
                OffsetDateTime.now()
        );

        return checkInMapper.toResponseDto(
                checkInRepository.save(checkIn)
        );
    }

    @Override
    public CheckInResponse marcarAtendido(Long id) {

        CheckIn checkIn = obtenerEntidad(id);

        if (checkIn.getEstado() != EstadoCheckIn.NOTIFICADO) {

            throw new ConflictException(
                    "El check-in debe estar NOTIFICADO " +
                            "antes de marcarlo como ATENDIDO"
            );
        }

        checkIn.setEstado(EstadoCheckIn.ATENDIDO);
        checkIn.setFechaAtencion(
                OffsetDateTime.now()
        );

        return checkInMapper.toResponseDto(
                checkInRepository.save(checkIn)
        );
    }

    private CheckIn obtenerEntidad(Long id) {

        return checkInRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Check-in no encontrado con id: " + id
                        )
                );
    }

    private OffsetDateTime calcularFechaDisponible(
            Tramo tramo
    ) {

        if (tramo.getFechaHoraSalida() == null) {

            throw new ConflictException(
                    "El tramo no tiene fecha y hora de salida"
            );
        }

        return tramo.getFechaHoraSalida()
                .minusHours(48);
    }
}