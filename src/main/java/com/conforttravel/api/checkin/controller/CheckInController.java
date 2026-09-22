package com.conforttravel.api.checkin.controller;

import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.service.CheckInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkins")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping
    public ResponseEntity<CheckInResponse> crear(
            @Valid @RequestBody CheckInRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(checkInService.crear(request));
    }

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