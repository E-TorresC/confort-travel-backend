package com.conforttravel.api.notificacion.controller;

import com.conforttravel.api.notificacion.dto.NotificacionResponse;
import com.conforttravel.api.notificacion.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> obtenerTodas() {

        return ResponseEntity.ok(
                notificacionService.obtenerTodas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> obtenerPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                notificacionService.obtenerPorId(id)
        );
    }

    @GetMapping("/no-leidas")
    public ResponseEntity<List<NotificacionResponse>> obtenerNoLeidas() {

        return ResponseEntity.ok(
                notificacionService.obtenerNoLeidas()
        );
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<NotificacionResponse> marcarComoLeida(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                notificacionService.marcarComoLeida(id)
        );
    }
}