package com.conforttravel.api.venta.controller;

import com.conforttravel.api.venta.dto.VentaRequest;
import com.conforttravel.api.venta.dto.VentaResponse;
import com.conforttravel.api.venta.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponse>> obtenerTodos() {
        return ResponseEntity.ok(ventaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                ventaService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<VentaResponse> crear(
            @Valid @RequestBody VentaRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ventaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequest request
    ) {
        return ResponseEntity.ok(
                ventaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}