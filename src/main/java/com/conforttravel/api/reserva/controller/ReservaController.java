package com.conforttravel.api.reserva.controller;

import com.conforttravel.api.reserva.dto.ReservaRequest;
import com.conforttravel.api.reserva.dto.ReservaResponse;
import com.conforttravel.api.reserva.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> obtenerTodos() {
        return ResponseEntity.ok(reservaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                reservaService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> crear(
            @Valid @RequestBody ReservaRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequest request
    ) {
        return ResponseEntity.ok(
                reservaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}