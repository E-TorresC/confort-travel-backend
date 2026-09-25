package com.conforttravel.api.auditoria.controller;

import com.conforttravel.api.auditoria.dto.AuditLogFilterRequest;
import com.conforttravel.api.auditoria.dto.AuditLogRequest;
import com.conforttravel.api.auditoria.dto.AuditLogResponse;
import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.shared.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

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

    @GetMapping("/page")
    public ResponseEntity<PageResponse<AuditLogResponse>> buscar(

            @RequestParam(required = false) Long usuarioId,

            @RequestParam(required = false) String accion,

            @RequestParam(required = false) String entidad,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime fechaDesde,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime fechaHasta,

            @PageableDefault(
                    size = 10,
                    sort = "fecha",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {

        AuditLogFilterRequest filter = new AuditLogFilterRequest(
                usuarioId,
                accion,
                entidad,
                fechaDesde,
                fechaHasta
        );

        return ResponseEntity.ok(
                auditLogService.buscar(filter, pageable)
        );
    }
}