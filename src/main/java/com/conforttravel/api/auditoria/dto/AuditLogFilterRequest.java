package com.conforttravel.api.auditoria.dto;

import java.time.OffsetDateTime;

public record AuditLogFilterRequest(
        Long usuarioId,
        String accion,
        String entidad,
        OffsetDateTime fechaDesde,
        OffsetDateTime fechaHasta
) {
}