package com.conforttravel.api.usuario.service.impl;

import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.usuario.dto.UsuarioRequest;
import com.conforttravel.api.usuario.dto.UsuarioResponse;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.mapper.UsuarioMapper;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import com.conforttravel.api.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);

        /*
         * La gestión de passwordHash se completará junto con
         * la configuración de seguridad/autenticación.
         */
        usuario.setPasswordHash(passwordEncoder.encode(request.password()));

        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        Usuario guardado = usuarioRepository.save(usuario);

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
    public UsuarioResponse actualizar(
            Long id,
            UsuarioRequest request
    ) {
        Usuario usuario = obtenerEntidad(id);

        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setEmail(request.email());
        usuario.setRol(request.rol());

        if (request.activo() != null) {
            usuario.setActivo(request.activo());
        }

        /*
         * La actualización segura de la contraseña se completará
         * al integrar PasswordEncoder en la fase de seguridad.
         */
        usuario.setPasswordHash(request.password());

        Usuario actualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Usuario usuario = obtenerEntidad(id);

        usuarioRepository.delete(usuario);
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