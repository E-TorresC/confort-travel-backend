package com.conforttravel.api.auditoria.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record AuditLogResponse(

        Long id,

        Long usuarioId,

        String accion,

        String entidad,

        Long entidadId,

        OffsetDateTime fecha,

        String detalle
) {
}