package com.conforttravel.api.usuario.controller;

import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.usuario.dto.*;
import com.conforttravel.api.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> obtenerTodos() {
        return ResponseEntity.ok(
                usuarioService.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                usuarioService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(
            @Valid @RequestBody UsuarioCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateRequest request
    ) {
        return ResponseEntity.ok(
                usuarioService.actualizar(id, request)
        );
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UsuarioResponse> cambiarPassword(
            @PathVariable Long id,
            @Valid @RequestBody CambiarPasswordRequest request
    ) {
        return ResponseEntity.ok(
                usuarioService.cambiarPassword(id, request)
        );
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<UsuarioResponse> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambiarEstadoRequest request
    ) {
        return ResponseEntity.ok(
                usuarioService.cambiarEstado(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponse<UsuarioResponse>> buscar(

            @RequestParam(required = false) String nombre,

            @RequestParam(required = false) String apellido,

            @RequestParam(required = false) String email,

            @RequestParam(required = false) Boolean activo,

            Pageable pageable

    ) {

        UsuarioFilterRequest filter =
                new UsuarioFilterRequest(
                        nombre,
                        apellido,
                        email,
                        activo
                );

        return ResponseEntity.ok(
                usuarioService.buscar(filter, pageable)
        );
    }
}