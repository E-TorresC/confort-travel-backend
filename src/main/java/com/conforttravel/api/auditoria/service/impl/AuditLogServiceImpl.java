package com.conforttravel.api.auditoria.service.impl;

import com.conforttravel.api.auditoria.dto.AuditLogRequest;
import com.conforttravel.api.auditoria.dto.AuditLogResponse;
import com.conforttravel.api.auditoria.entity.AuditLog;
import com.conforttravel.api.auditoria.repository.AuditLogRepository;
import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public AuditLogResponse registrar(AuditLogRequest request) {

        Usuario usuario = obtenerUsuarioAutenticado();

        AuditLog auditLog = AuditLog.builder()
                .usuario(usuario)
                .accion(request.accion())
                .entidad(request.entidad())
                .entidadId(request.entidadId())
                .detalle(request.detalle())
                .build();

        AuditLog guardado = auditLogRepository.save(auditLog);

        return toResponse(guardado);
    }

    @Override
    public PageResponse<AuditLogResponse> listar(Pageable pageable) {

        return PageResponse.from(
                auditLogRepository
                        .findAll(pageable)
                        .map(this::toResponse)
        );
    }

    @Override
    public PageResponse<AuditLogResponse> listarPorUsuario(
            Long usuarioId,
            Pageable pageable
    ) {

        return PageResponse.from(
                auditLogRepository
                        .findByUsuarioId(usuarioId, pageable)
                        .map(this::toResponse)
        );
    }

    @Override
    public PageResponse<AuditLogResponse> listarPorEntidad(
            String entidad,
            Pageable pageable
    ) {

        return PageResponse.from(
                auditLogRepository
                        .findByEntidadIgnoreCase(entidad, pageable)
                        .map(this::toResponse)
        );
    }

    @Override
    public PageResponse<AuditLogResponse> listarPorAccion(
            String accion,
            Pageable pageable
    ) {

        return PageResponse.from(
                auditLogRepository
                        .findByAccionIgnoreCase(accion, pageable)
                        .map(this::toResponse)
        );
    }

    private Usuario obtenerUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new IllegalStateException(
                    "No existe un usuario autenticado"
            );
        }

        String email = authentication.getName();

        return usuarioRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario autenticado no encontrado"
                        )
                );
    }

    private AuditLogResponse toResponse(AuditLog auditLog) {

        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getUsuario() != null
                        ? auditLog.getUsuario().getId()
                        : null,
                auditLog.getAccion(),
                auditLog.getEntidad(),
                auditLog.getEntidadId(),
                auditLog.getFecha(),
                auditLog.getDetalle()
        );
    }
}