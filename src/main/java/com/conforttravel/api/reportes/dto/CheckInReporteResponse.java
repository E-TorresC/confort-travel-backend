package com.conforttravel.api.reportes.dto;

import com.conforttravel.api.checkin.entity.EstadoCheckIn;

import java.time.OffsetDateTime;

public record CheckInReporteResponse(

        Long checkInId,

        EstadoCheckIn estado,

        OffsetDateTime fechaDisponible,

        OffsetDateTime fechaNotificacion,

        OffsetDateTime fechaAtencion,

        Long tramoId,

        String origen,

        String destino,

        OffsetDateTime fechaHoraSalida

) {
}