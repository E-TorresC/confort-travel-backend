package com.conforttravel.api.reserva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReservaRequest(

        @NotBlank(message = "El código de reserva es obligatorio")
        @Size(max = 50, message = "El código de reserva no puede superar los 50 caracteres")
        String codigoReserva,

        @NotNull(message = "El tramo es obligatorio")
        Long tramoId,

        @NotNull(message = "La aerolínea es obligatoria")
        Long aerolineaId
) {
}