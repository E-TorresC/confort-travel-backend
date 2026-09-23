package com.conforttravel.api.auditoria.service;

import com.conforttravel.api.auditoria.dto.AuditLogRequest;
import com.conforttravel.api.auditoria.dto.AuditLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogService {

    AuditLogResponse registrar(AuditLogRequest request);

    default AuditLogResponse registrar(
            String accion,
            String entidad,
            Long entidadId,
            String detalle
    ) {
        return registrar(
                new AuditLogRequest(
                        null,
                        accion,
                        entidad,
                        entidadId,
                        detalle
                )
        );
    }

    Page<AuditLogResponse> listar(Pageable pageable);

    Page<AuditLogResponse> listarPorUsuario(
            Long usuarioId,
            Pageable pageable
    );

    Page<AuditLogResponse> listarPorEntidad(
            String entidad,
            Pageable pageable
    );

    Page<AuditLogResponse> listarPorAccion(
            String accion,
            Pageable pageable
    );
}