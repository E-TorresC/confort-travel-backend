package com.conforttravel.api.auditoria.controller;

import com.conforttravel.api.auditoria.dto.AuditLogRequest;
import com.conforttravel.api.auditoria.dto.AuditLogResponse;
import com.conforttravel.api.auditoria.service.AuditLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogResponse> registrar(
            @Valid @RequestBody AuditLogRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(auditLogService.registrar(request));
    }

    @GetMapping
    public ResponseEntity<Page<AuditLogResponse>> listar(
            @PageableDefault(
                    size = 10,
                    sort = "fecha"
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                auditLogService.listar(pageable)
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<AuditLogResponse>> listarPorUsuario(
            @PathVariable Long usuarioId,
            @PageableDefault(
                    size = 10,
                    sort = "fecha"
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                auditLogService.listarPorUsuario(
                        usuarioId,
                        pageable
                )
        );
    }

    @GetMapping("/entidad/{entidad}")
    public ResponseEntity<Page<AuditLogResponse>> listarPorEntidad(
            @PathVariable String entidad,
            @PageableDefault(
                    size = 10,
                    sort = "fecha"
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                auditLogService.listarPorEntidad(
                        entidad,
                        pageable
                )
        );
    }

    @GetMapping("/accion/{accion}")
    public ResponseEntity<Page<AuditLogResponse>> listarPorAccion(
            @PathVariable String accion,
            @PageableDefault(
                    size = 10,
                    sort = "fecha"
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                auditLogService.listarPorAccion(
                        accion,
                        pageable
                )
        );
    }
}