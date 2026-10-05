package com.conforttravel.api.checkin.dto;

import com.conforttravel.api.checkin.entity.EstadoCheckIn;

import java.time.OffsetDateTime;

public record CheckInResponse(

        Long id,
        EstadoCheckIn estado,
        OffsetDateTime fechaDisponible,
        OffsetDateTime fechaNotificacion,
        OffsetDateTime fechaAtencion,
        Long tramoId,
        String tramoCodigo,
        String clienteNombres,
        String clienteApellidos,
        String reservaCodigo
) {
}