package com.conforttravel.api.aerolinea.dto;

public record AerolineaFilterRequest(

        String nombre,

        String codigo,

        Boolean activo

) {
}