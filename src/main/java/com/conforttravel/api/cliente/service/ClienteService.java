package com.conforttravel.api.cliente.service;

import com.conforttravel.api.cliente.dto.ClienteFilterRequest;
import com.conforttravel.api.cliente.dto.ClienteRequest;
import com.conforttravel.api.cliente.dto.ClienteResponse;
import com.conforttravel.api.shared.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    ClienteResponse crear(ClienteRequest request);

    ClienteResponse obtenerPorId(Long id);

    List<ClienteResponse> obtenerTodos();

    ClienteResponse actualizar(Long id, ClienteRequest request);

    void eliminar(Long id);

    PageResponse<ClienteResponse> buscar(
            ClienteFilterRequest filter,
            Pageable pageable
    );
}