package com.conforttravel.api.auditoria.service;

import com.conforttravel.api.auditoria.dto.AuditLogFilterRequest;
import com.conforttravel.api.auditoria.dto.AuditLogRequest;
import com.conforttravel.api.auditoria.dto.AuditLogResponse;
import com.conforttravel.api.shared.dto.PageResponse;
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

    PageResponse<AuditLogResponse> buscar(
            AuditLogFilterRequest filter,
            Pageable pageable
    );
}