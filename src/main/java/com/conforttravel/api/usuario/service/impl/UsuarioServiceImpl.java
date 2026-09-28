package com.conforttravel.api.usuario.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.usuario.dto.*;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.mapper.UsuarioMapper;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import com.conforttravel.api.usuario.service.UsuarioService;
import com.conforttravel.api.usuario.specification.UsuarioSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;
    private final UsuarioSpecificationBuilder usuarioSpecificationBuilder;

    @Override
    @Transactional
    public UsuarioResponse crear(UsuarioCreateRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);

        usuario.setPasswordHash(passwordEncoder.encode(request.password()));

        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        Usuario guardado = usuarioRepository.save(usuario);

        auditLogService.registrar(
                "CREAR",
                "USUARIO",
                guardado.getId(),
                "Se creó el usuario: " + guardado.getEmail()
        );

        return usuarioMapper.toResponseDto(guardado);
    }

    @Override
    public UsuarioResponse obtenerPorId(Long id) {
        return usuarioMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<UsuarioResponse> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest request) {
        Usuario usuario = obtenerEntidad(id);

        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setEmail(request.email());
        usuario.setRol(request.rol());

        if (request.activo() != null) {
            usuario.setActivo(request.activo());
        }

        Usuario actualizado = usuarioRepository.save(usuario);

        auditLogService.registrar(
                "ACTUALIZAR",
                "USUARIO",
                actualizado.getId(),
                "Se actualizaron los datos del usuario: " + actualizado.getEmail()
        );

        return usuarioMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public UsuarioResponse cambiarPassword(Long id, CambiarPasswordRequest request) {
        Usuario usuario = obtenerEntidad(id);

        usuario.setPasswordHash(passwordEncoder.encode(request.password()));

        Usuario actualizado = usuarioRepository.save(usuario);

        auditLogService.registrar(
                "ACTUALIZAR_PASSWORD",
                "USUARIO",
                actualizado.getId(),
                "Se cambió la contraseña del usuario: " + actualizado.getEmail()
        );

        return usuarioMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public UsuarioResponse cambiarEstado(Long id, CambiarEstadoRequest request) {
        Usuario usuario = obtenerEntidad(id);

        usuario.setActivo(request.activo());

        Usuario actualizado = usuarioRepository.save(usuario);

        auditLogService.registrar(
                "CAMBIAR_ESTADO",
                "USUARIO",
                actualizado.getId(),
                "Se actualizó el estado del usuario a " + (actualizado.getActivo() ? "ACTIVO" : "INACTIVO") + ": " + actualizado.getEmail()
        );

        return usuarioMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Usuario usuario = obtenerEntidad(id);

        auditLogService.registrar(
                "ELIMINAR",
                "USUARIO",
                usuario.getId(),
                "Se eliminó el usuario: " + usuario.getEmail()
        );

        usuarioRepository.delete(usuario);
    }

    @Override
    public PageResponse<UsuarioResponse> buscar(
            UsuarioFilterRequest filter,
            Pageable pageable
    ) {
        return PageResponse.from(
                usuarioRepository
                        .findAll(
                                usuarioSpecificationBuilder.build(filter),
                                pageable
                        )
                        .map(usuarioMapper::toResponseDto)
        );
    }

    private Usuario obtenerEntidad(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado con id: " + id
                        )
                );
    }
}