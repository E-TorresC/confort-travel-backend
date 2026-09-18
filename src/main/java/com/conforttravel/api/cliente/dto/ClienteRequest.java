package com.conforttravel.api.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(max = 150, message = "Los nombres no pueden superar los 150 caracteres")
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 150, message = "Los apellidos no pueden superar los 150 caracteres")
        String apellidos,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(max = 30, message = "El teléfono no puede superar los 30 caracteres")
        String telefono
) {
}