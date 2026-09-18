package com.conforttravel.api.reserva.dto;

public record ReservaResponse(

        Long id,

        String codigoReserva,

        Long tramoId,

        Long aerolineaId
) {
}