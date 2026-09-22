package com.conforttravel.api.checkin.service;

import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;

import java.util.List;

public interface CheckInService {

    CheckInResponse crear(CheckInRequest request);

    CheckInResponse obtenerPorId(Long id);

    List<CheckInResponse> obtenerTodos();


    CheckInResponse obtenerPorTramo(Long tramoId);


    List<CheckInResponse> obtenerPorEstado(
            EstadoCheckIn estado
    );

    CheckInResponse marcarNotificado(Long id);

    CheckInResponse marcarAtendido(Long id);
}