package com.conforttravel.api.tramo.dto;

import com.conforttravel.api.tramo.entity.TipoTramo;
import com.conforttravel.api.tramo.entity.TipoViaje;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record TramoRequest(

        @NotNull(message = "El tipo de tramo es obligatorio")
        TipoTramo tipoTramo,

        @NotNull(message = "El tipo de viaje es obligatorio")
        TipoViaje tipoViaje,

        @NotBlank(message = "El origen es obligatorio")
        @Size(max = 150, message = "El origen no puede superar los 150 caracteres")
        String origen,

        @NotBlank(message = "El destino es obligatorio")
        @Size(max = 150, message = "El destino no puede superar los 150 caracteres")
        String destino,

        @NotNull(message = "La fecha/hora de salida es obligatoria")
        OffsetDateTime fechaHoraSalida,

        OffsetDateTime fechaHoraLlegada,

        @NotNull(message = "El viaje es obligatorio")
        Long viajeId
) {
}