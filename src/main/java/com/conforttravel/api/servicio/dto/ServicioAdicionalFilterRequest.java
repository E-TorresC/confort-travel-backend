package com.conforttravel.api.servicio.dto;

public record ServicioAdicionalFilterRequest(
        String nombre,
        Boolean activo
) {
}