package com.conforttravel.api.venta.controller;

import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.venta.dto.VentaFilterRequest;
import com.conforttravel.api.venta.dto.VentaRequest;
import com.conforttravel.api.venta.dto.VentaResponse;
import com.conforttravel.api.venta.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @GetMapping("/page")
    public ResponseEntity<PageResponse<VentaResponse>> buscar(

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fechaDesde,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fechaHasta,

            @RequestParam(required = false)
            BigDecimal precioMinimo,

            @RequestParam(required = false)
            BigDecimal precioMaximo,

            @RequestParam(required = false)
            Long clienteId,

            @RequestParam(required = false)
            Long agenteId,

            Pageable pageable

    ) {

        VentaFilterRequest filter =
                new VentaFilterRequest(
                        fechaDesde,
                        fechaHasta,
                        precioMinimo,
                        precioMaximo,
                        clienteId,
                        agenteId
                );

        return ResponseEntity.ok(
                ventaService.buscar(filter, pageable)
        );
    }
}