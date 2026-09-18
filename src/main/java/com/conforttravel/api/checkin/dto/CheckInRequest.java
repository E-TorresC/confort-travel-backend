package com.conforttravel.api.checkin.dto;

import jakarta.validation.constraints.NotNull;

public record CheckInRequest(

        @NotNull(message = "El tramo es obligatorio")
        Long tramoId
) {
}