package com.conforttravel.api.reserva.dto;

public record ReservaResponse(

        Long id,
        String codigoReserva,
        Long tramoId,
        String tramoCodigo,
        Long aerolineaId,
        String aerolineaNombre,
        String clienteNombres,
        String clienteApellidos
) {
}