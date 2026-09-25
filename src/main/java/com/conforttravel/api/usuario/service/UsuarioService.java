package com.conforttravel.api.usuario.service;

import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.usuario.dto.UsuarioFilterRequest;
import com.conforttravel.api.usuario.dto.UsuarioRequest;
import com.conforttravel.api.usuario.dto.UsuarioResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse crear(UsuarioRequest request);

    UsuarioResponse obtenerPorId(Long id);

    List<UsuarioResponse> obtenerTodos();

    UsuarioResponse actualizar(Long id, UsuarioRequest request);

    void eliminar(Long id);

    PageResponse<UsuarioResponse> buscar(
            UsuarioFilterRequest filter,
            Pageable pageable
    );
}