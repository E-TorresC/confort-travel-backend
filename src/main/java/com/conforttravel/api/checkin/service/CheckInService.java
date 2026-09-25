package com.conforttravel.api.checkin.service;

import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CheckInService {

    CheckInResponse crear(CheckInRequest request);

    CheckInResponse obtenerPorId(Long id);

    List<CheckInResponse> obtenerTodos();

    CheckInResponse obtenerPorTramo(Long tramoId);

    List<CheckInResponse> obtenerPorEstado(
            EstadoCheckIn estado
    );

    // Nuevo método para búsqueda paginada
    PageResponse<CheckInResponse> buscar(
            EstadoCheckIn estado,
            Pageable pageable
    );

    CheckInResponse marcarAtendido(Long id);
}