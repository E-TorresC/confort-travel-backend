package com.conforttravel.api.auth.dto;

public record LoginResponse(

        String token,

        String type,

        Long userId,

        String nombre,

        String apellido,

        String email,

        String rol
) {
}