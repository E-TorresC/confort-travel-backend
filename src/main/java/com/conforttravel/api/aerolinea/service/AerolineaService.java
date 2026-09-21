package com.conforttravel.api.aerolinea.service;

import com.conforttravel.api.aerolinea.dto.AerolineaFilterRequest;
import com.conforttravel.api.aerolinea.dto.AerolineaRequest;
import com.conforttravel.api.aerolinea.dto.AerolineaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AerolineaService {

    AerolineaResponse crear(AerolineaRequest request);

    AerolineaResponse obtenerPorId(Long id);

    List<AerolineaResponse> obtenerTodos();

    AerolineaResponse actualizar(Long id, AerolineaRequest request);

    void eliminar(Long id);

    Page<AerolineaResponse> buscar(
            AerolineaFilterRequest filter,
            Pageable pageable
    );
}