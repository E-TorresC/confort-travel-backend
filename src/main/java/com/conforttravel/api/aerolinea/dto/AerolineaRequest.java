package com.conforttravel.api.aerolinea.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AerolineaRequest(

        @NotBlank(message = "El nombre de la aerolínea es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @Size(max = 20, message = "El código no puede superar los 20 caracteres")
        String codigo,

        Boolean activo
) {
}