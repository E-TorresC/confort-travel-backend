package com.conforttravel.api.viaje.controller;

import com.conforttravel.api.viaje.dto.ViajeRequest;
import com.conforttravel.api.viaje.dto.ViajeResponse;
import com.conforttravel.api.viaje.service.ViajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viajes")
@RequiredArgsConstructor
public class ViajeController {

    private final ViajeService viajeService;

    @GetMapping
    public ResponseEntity<List<ViajeResponse>> obtenerTodos() {
        return ResponseEntity.ok(viajeService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                viajeService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ViajeResponse> crear(
            @Valid @RequestBody ViajeRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(viajeService.crear(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        viajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}