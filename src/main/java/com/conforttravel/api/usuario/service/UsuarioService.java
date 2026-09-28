package com.conforttravel.api.usuario.service;

import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.usuario.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse crear(UsuarioCreateRequest request);

    UsuarioResponse obtenerPorId(Long id);

    List<UsuarioResponse> obtenerTodos();

    UsuarioResponse actualizar(Long id, UsuarioUpdateRequest request);

    UsuarioResponse cambiarPassword(Long id, CambiarPasswordRequest request);

    UsuarioResponse cambiarEstado(Long id, CambiarEstadoRequest request);

    void eliminar(Long id);

    PageResponse<UsuarioResponse> buscar(
            UsuarioFilterRequest filter,
            Pageable pageable
    );
}