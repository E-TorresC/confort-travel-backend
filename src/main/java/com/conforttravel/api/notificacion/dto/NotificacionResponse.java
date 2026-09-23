package com.conforttravel.api.notificacion.dto;

import com.conforttravel.api.notificacion.entity.TipoNotificacion;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record NotificacionResponse(

        Long id,

        TipoNotificacion tipo,

        String mensaje,

        OffsetDateTime fechaCreacion,

        OffsetDateTime fechaLectura,

        Long usuarioId,

        Long checkInId
) {
}