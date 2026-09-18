package com.conforttravel.api.auditoria.dto;

import java.time.LocalDateTime;

public record AuditLogResponse(

        Long id,

        Long usuarioId,

        String accion,

        String entidad,

        Long entidadId,

        LocalDateTime fecha,

        String detalle
) {
}