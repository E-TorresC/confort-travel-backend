package com.conforttravel.api.venta.controller;

import com.conforttravel.api.venta.dto.VentaServicioRequest;
import com.conforttravel.api.venta.dto.VentaServicioResponse;
import com.conforttravel.api.venta.service.VentaServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venta-servicios")
@RequiredArgsConstructor
public class VentaServicioController {

    private final VentaServicioService ventaServicioService;

    @GetMapping
    public ResponseEntity<List<VentaServicioResponse>> obtenerTodos() {
        return ResponseEntity.ok(
                ventaServicioService.obtenerTodos()
        );
    }

    @GetMapping("/{ventaId}/{servicioAdicionalId}")
    public ResponseEntity<VentaServicioResponse> obtenerPorId(
            @PathVariable Long ventaId,
            @PathVariable Long servicioAdicionalId
    ) {
        return ResponseEntity.ok(
                ventaServicioService.obtenerPorId(
                        ventaId,
                        servicioAdicionalId
                )
        );
    }

    @PostMapping
    public ResponseEntity<VentaServicioResponse> agregar(
            @Valid @RequestBody VentaServicioRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ventaServicioService.agregar(request));
    }

    @DeleteMapping("/{ventaId}/{servicioAdicionalId}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long ventaId,
            @PathVariable Long servicioAdicionalId
    ) {
        ventaServicioService.eliminar(
                ventaId,
                servicioAdicionalId
        );

        return ResponseEntity.noContent().build();
    }
}