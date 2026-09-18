package com.conforttravel.api.checkin.controller;

import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.service.CheckInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check-ins")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @GetMapping
    public ResponseEntity<List<CheckInResponse>> obtenerTodos() {
        return ResponseEntity.ok(checkInService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckInResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                checkInService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<CheckInResponse> crear(
            @Valid @RequestBody CheckInRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(checkInService.crear(request));
    }

    @PatchMapping("/{id}/notificar")
    public ResponseEntity<CheckInResponse> notificar(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                checkInService.notificar(id)
        );
    }

    @PatchMapping("/{id}/atender")
    public ResponseEntity<CheckInResponse> atender(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                checkInService.atender(id)
        );
    }
}