package com.conforttravel.api.notificacion.service;

import com.conforttravel.api.notificacion.dto.NotificacionResponse;

import java.util.List;

public interface NotificacionService {

    List<NotificacionResponse> obtenerTodas();

    NotificacionResponse obtenerPorId(Long id);

    List<NotificacionResponse> obtenerNoLeidas();

    NotificacionResponse marcarComoLeida(Long id);
}