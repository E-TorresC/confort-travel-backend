package com.conforttravel.api.venta.service;

import com.conforttravel.api.venta.dto.VentaRequest;
import com.conforttravel.api.venta.dto.VentaResponse;

import java.util.List;

public interface VentaService {

    VentaResponse crear(VentaRequest request);

    VentaResponse obtenerPorId(Long id);

    List<VentaResponse> obtenerTodos();

    VentaResponse actualizar(Long id, VentaRequest request);

    void eliminar(Long id);
}