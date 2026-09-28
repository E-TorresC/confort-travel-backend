package com.conforttravel.api.tramo.service;

import com.conforttravel.api.tramo.dto.TramoRequest;
import com.conforttravel.api.tramo.dto.TramoResponse;

import java.util.List;

public interface TramoService {

    TramoResponse crear(TramoRequest request);

    TramoResponse obtenerPorId(Long id);

    List<TramoResponse> obtenerTodos();

    List<TramoResponse> obtenerPorViaje(Long viajeId);

    TramoResponse actualizar(Long id, TramoRequest request);

    void eliminar(Long id);
}