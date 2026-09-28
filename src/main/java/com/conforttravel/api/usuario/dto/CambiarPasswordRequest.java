package com.conforttravel.api.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambiarPasswordRequest(

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
        String password
) {
}