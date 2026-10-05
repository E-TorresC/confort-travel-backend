package com.conforttravel.api.tramo.dto;

import com.conforttravel.api.tramo.entity.TipoTramo;
import com.conforttravel.api.tramo.entity.TipoViaje;

import java.time.OffsetDateTime;

public record TramoResponse(

        Long id,
        String codigo,
        TipoTramo tipoTramo,
        TipoViaje tipoViaje,
        String origen,
        String destino,
        OffsetDateTime fechaHoraSalida,
        OffsetDateTime fechaHoraLlegada,
        Long ventaId,
        Long reservaId,
        Long checkInId,
        String clienteNombres,
        String clienteApellidos
) {
}