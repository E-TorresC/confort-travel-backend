package com.conforttravel.api.tramo.service.impl;

import com.conforttravel.api.shared.exception.BusinessException;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.tramo.dto.TramoRequest;
import com.conforttravel.api.tramo.dto.TramoResponse;
import com.conforttravel.api.tramo.entity.Tramo;
import com.conforttravel.api.tramo.mapper.TramoMapper;
import com.conforttravel.api.tramo.repository.TramoRepository;
import com.conforttravel.api.tramo.service.TramoService;
import com.conforttravel.api.viaje.entity.Viaje;
import com.conforttravel.api.viaje.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TramoServiceImpl implements TramoService {

    private final TramoRepository tramoRepository;
    private final ViajeRepository viajeRepository;
    private final TramoMapper tramoMapper;

    @Override
    @Transactional
    public TramoResponse crear(TramoRequest request) {
        validarFechas(request);

        Viaje viaje = obtenerViaje(request.viajeId());

        Tramo tramo = tramoMapper.toEntity(request);
        tramo.setViaje(viaje);

        Tramo guardado = tramoRepository.save(tramo);

        return tramoMapper.toResponseDto(guardado);
    }

    @Override
    public TramoResponse obtenerPorId(Long id) {
        return tramoMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<TramoResponse> obtenerTodos() {
        return tramoRepository.findAll()
                .stream()
                .map(tramoMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public TramoResponse actualizar(
            Long id,
            TramoRequest request
    ) {
        validarFechas(request);

        Tramo tramo = obtenerEntidad(id);
        Viaje viaje = obtenerViaje(request.viajeId());

        tramo.setTipoTramo(request.tipoTramo());
        tramo.setTipoViaje(request.tipoViaje());
        tramo.setOrigen(request.origen());
        tramo.setDestino(request.destino());
        tramo.setFechaHoraSalida(request.fechaHoraSalida());
        tramo.setFechaHoraLlegada(request.fechaHoraLlegada());
        tramo.setViaje(viaje);

        Tramo actualizado = tramoRepository.save(tramo);

        return tramoMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Tramo tramo = obtenerEntidad(id);

        tramoRepository.delete(tramo);
    }

    private Tramo obtenerEntidad(Long id) {
        return tramoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tramo no encontrado con id: " + id
                        )
                );
    }

    private Viaje obtenerViaje(Long id) {
        return viajeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Viaje no encontrado con id: " + id
                        )
                );
    }

    private void validarFechas(TramoRequest request) {
        if (request.fechaHoraLlegada() != null
                && !request.fechaHoraLlegada()
                .isAfter(request.fechaHoraSalida())) {

            throw new BusinessException(
                    "La fecha/hora de llegada debe ser posterior "
                            + "a la fecha/hora de salida"
            );
        }
    }
}