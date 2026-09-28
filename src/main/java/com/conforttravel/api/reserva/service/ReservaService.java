package com.conforttravel.api.reserva.service;

import com.conforttravel.api.reserva.dto.ReservaRequest;
import com.conforttravel.api.reserva.dto.ReservaResponse;

import java.util.List;

public interface ReservaService {

    ReservaResponse crear(ReservaRequest request);

    ReservaResponse obtenerPorId(Long id);

    List<ReservaResponse> obtenerTodos();

    ReservaResponse obtenerPorTramo(Long tramoId);

    ReservaResponse actualizar(Long id, ReservaRequest request);

    void eliminar(Long id);
}