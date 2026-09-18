package com.conforttravel.api.tramo.dto;

import com.conforttravel.api.tramo.entity.TipoTramo;
import com.conforttravel.api.tramo.entity.TipoViaje;

import java.time.OffsetDateTime;

public record TramoResponse(

        Long id,

        TipoTramo tipoTramo,

        TipoViaje tipoViaje,

        String origen,

        String destino,

        OffsetDateTime fechaHoraSalida,

        OffsetDateTime fechaHoraLlegada,

        Long viajeId,

        Long reservaId,

        Long checkInId
) {
}