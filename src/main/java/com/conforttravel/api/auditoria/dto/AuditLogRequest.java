package com.conforttravel.api.auditoria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuditLogRequest(

        Long usuarioId,

        @NotBlank(message = "La acción es obligatoria")
        @Size(max = 50, message = "La acción no puede superar los 50 caracteres")
        String accion,

        @NotBlank(message = "La entidad es obligatoria")
        @Size(max = 100, message = "La entidad no puede superar los 100 caracteres")
        String entidad,

        Long entidadId,

        @Size(max = 2000, message = "El detalle no puede superar los 2000 caracteres")
        String detalle
) {
}