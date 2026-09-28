package com.conforttravel.api.venta.service;

import com.conforttravel.api.venta.dto.VentaServicioRequest;
import com.conforttravel.api.venta.dto.VentaServicioResponse;

import java.util.List;

public interface VentaServicioService {

    VentaServicioResponse agregar(VentaServicioRequest request);

    VentaServicioResponse obtenerPorId(
            Long ventaId,
            Long servicioAdicionalId
    );

    List<VentaServicioResponse> obtenerTodos();

    List<VentaServicioResponse> obtenerPorVenta(Long ventaId);

    void eliminar(
            Long ventaId,
            Long servicioAdicionalId
    );
}