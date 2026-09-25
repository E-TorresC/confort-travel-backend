package com.conforttravel.api.checkin.controller;

import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkins")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @GetMapping
    public ResponseEntity<List<CheckInResponse>> obtenerTodos() {
        return ResponseEntity.ok(
                checkInService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckInResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                checkInService.obtenerPorId(id)
        );
    }

    @GetMapping("/tramo/{tramoId}")
    public ResponseEntity<CheckInResponse> obtenerPorTramo(
            @PathVariable Long tramoId
    ) {
        return ResponseEntity.ok(
                checkInService.obtenerPorTramo(tramoId)
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CheckInResponse>> obtenerPorEstado(
            @PathVariable EstadoCheckIn estado
    ) {
        return ResponseEntity.ok(
                checkInService.obtenerPorEstado(estado)
        );
    }

    @PatchMapping("/{id}/notificar")
    public ResponseEntity<CheckInResponse> marcarNotificado(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                checkInService.marcarNotificado(id)
        );
    }

    @PatchMapping("/{id}/atender")
    public ResponseEntity<CheckInResponse> marcarAtendido(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                checkInService.marcarAtendido(id)
        );
    }
}