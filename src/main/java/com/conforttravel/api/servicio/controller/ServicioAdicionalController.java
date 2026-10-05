package com.conforttravel.api.servicio.controller;

import com.conforttravel.api.servicio.dto.ServicioAdicionalFilterRequest;
import com.conforttravel.api.servicio.dto.ServicioAdicionalRequest;
import com.conforttravel.api.servicio.dto.ServicioAdicionalResponse;
import com.conforttravel.api.servicio.service.ServicioAdicionalService;
import com.conforttravel.api.shared.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class ServicioAdicionalController {

    private final ServicioAdicionalService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioAdicionalResponse>> obtenerTodos() {
        return ResponseEntity.ok(
                servicioService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioAdicionalResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                servicioService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ServicioAdicionalResponse> crear(
            @Valid @RequestBody ServicioAdicionalRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicioService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioAdicionalResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ServicioAdicionalRequest request
    ) {
        return ResponseEntity.ok(
                servicioService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponse<ServicioAdicionalResponse>> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Boolean activo,
            Pageable pageable
    ) {
        ServicioAdicionalFilterRequest filter = new ServicioAdicionalFilterRequest(nombre, activo);
        return ResponseEntity.ok(servicioService.buscar(filter, pageable));
    }
}