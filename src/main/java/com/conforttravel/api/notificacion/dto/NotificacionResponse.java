package com.conforttravel.api.notificacion.dto;

import com.conforttravel.api.notificacion.entity.TipoNotificacion;

import java.time.LocalDateTime;

public record NotificacionResponse(

        Long id,

        TipoNotificacion tipo,

        String mensaje,

        LocalDateTime fechaCreacion,

        LocalDateTime fechaLectura,

        Long usuarioId,

        Long checkInId
) {
}