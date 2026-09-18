package com.conforttravel.api.checkin.service;

import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.dto.CheckInResponse;

import java.util.List;

public interface CheckInService {

    CheckInResponse crear(CheckInRequest request);

    CheckInResponse obtenerPorId(Long id);

    List<CheckInResponse> obtenerTodos();

    CheckInResponse notificar(Long id);

    CheckInResponse atender(Long id);
}