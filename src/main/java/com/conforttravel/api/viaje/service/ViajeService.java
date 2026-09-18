package com.conforttravel.api.viaje.service;

import com.conforttravel.api.viaje.dto.ViajeRequest;
import com.conforttravel.api.viaje.dto.ViajeResponse;

import java.util.List;

public interface ViajeService {

    ViajeResponse crear(ViajeRequest request);

    ViajeResponse obtenerPorId(Long id);

    List<ViajeResponse> obtenerTodos();

    void eliminar(Long id);
}