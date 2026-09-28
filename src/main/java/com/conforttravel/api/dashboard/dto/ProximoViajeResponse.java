package com.conforttravel.api.dashboard.dto;

import java.time.OffsetDateTime;

public record ProximoViajeResponse(
        Long tramoId,
        String origen,
        String destino,
        OffsetDateTime fechaHoraSalida
) {
}