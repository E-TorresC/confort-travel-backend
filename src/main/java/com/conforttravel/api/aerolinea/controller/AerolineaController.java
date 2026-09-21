package com.conforttravel.api.aerolinea.controller;

import com.conforttravel.api.aerolinea.dto.AerolineaFilterRequest;
import com.conforttravel.api.aerolinea.dto.AerolineaRequest;
import com.conforttravel.api.aerolinea.dto.AerolineaResponse;
import com.conforttravel.api.aerolinea.service.AerolineaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aerolineas")
@RequiredArgsConstructor
public class AerolineaController {

    private final AerolineaService aerolineaService;

    @GetMapping
    public ResponseEntity<List<AerolineaResponse>> obtenerTodos() {
        return ResponseEntity.ok(aerolineaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AerolineaResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                aerolineaService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<AerolineaResponse> crear(
            @Valid @RequestBody AerolineaRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(aerolineaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AerolineaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AerolineaRequest request
    ) {
        return ResponseEntity.ok(
                aerolineaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        aerolineaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<AerolineaResponse>> buscar(

            @RequestParam(required = false) String nombre,

            @RequestParam(required = false) String codigo,

            @RequestParam(required = false) Boolean activo,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "desc") String direction

    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        AerolineaFilterRequest filter =
                new AerolineaFilterRequest(
                        nombre,
                        codigo,
                        activo
                );

        return ResponseEntity.ok(
                aerolineaService.buscar(filter, pageable)
        );
    }
}