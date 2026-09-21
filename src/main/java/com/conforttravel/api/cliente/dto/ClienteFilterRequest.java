package com.conforttravel.api.cliente.dto;

public record ClienteFilterRequest(

        String nombres,

        String apellidos,

        String telefono

) {
}