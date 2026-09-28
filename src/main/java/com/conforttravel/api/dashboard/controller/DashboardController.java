package com.conforttravel.api.dashboard.controller;

import com.conforttravel.api.dashboard.dto.DashboardResumenResponse;
import com.conforttravel.api.dashboard.dto.ProximoViajeResponse;
import com.conforttravel.api.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/resumen")
    public ResponseEntity<DashboardResumenResponse> obtenerResumen() {
        return ResponseEntity.ok(dashboardService.obtenerResumen());
    }

    @GetMapping("/proximos-viajes")
    public ResponseEntity<List<ProximoViajeResponse>> obtenerProximosViajes() {
        return ResponseEntity.ok(dashboardService.obtenerProximosViajes());
    }
}