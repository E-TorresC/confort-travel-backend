package com.conforttravel.api.servicio.service;

import com.conforttravel.api.servicio.dto.ServicioAdicionalFilterRequest;
import com.conforttravel.api.servicio.dto.ServicioAdicionalRequest;
import com.conforttravel.api.servicio.dto.ServicioAdicionalResponse;
import com.conforttravel.api.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServicioAdicionalService {

    ServicioAdicionalResponse crear(ServicioAdicionalRequest request);

    ServicioAdicionalResponse obtenerPorId(Long id);

    List<ServicioAdicionalResponse> obtenerTodos();

    ServicioAdicionalResponse actualizar(
            Long id,
            ServicioAdicionalRequest request
    );

    void eliminar(Long id);

    PageResponse<ServicioAdicionalResponse> buscar(ServicioAdicionalFilterRequest filter, Pageable pageable);
}