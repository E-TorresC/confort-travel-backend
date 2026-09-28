package com.conforttravel.api.tramo.controller;

import com.conforttravel.api.tramo.dto.TramoRequest;
import com.conforttravel.api.tramo.dto.TramoResponse;
import com.conforttravel.api.tramo.service.TramoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramos")
@RequiredArgsConstructor
public class TramoController {

    private final TramoService tramoService;

    @GetMapping
    public ResponseEntity<List<TramoResponse>> obtenerTodos() {
        return ResponseEntity.ok(tramoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramoResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                tramoService.obtenerPorId(id)
        );
    }

    @GetMapping("/viaje/{viajeId}")
    public ResponseEntity<List<TramoResponse>> obtenerPorViaje(
            @PathVariable Long viajeId
    ) {
        return ResponseEntity.ok(
                tramoService.obtenerPorViaje(viajeId)
        );
    }

    @PostMapping
    public ResponseEntity<TramoResponse> crear(
            @Valid @RequestBody TramoRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tramoService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TramoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TramoRequest request
    ) {
        return ResponseEntity.ok(
                tramoService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        tramoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}