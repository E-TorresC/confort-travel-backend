package com.conforttravel.api.usuario.dto;

public record UsuarioFilterRequest(

        String nombre,

        String apellido,

        String email,

        Boolean activo

) {
}