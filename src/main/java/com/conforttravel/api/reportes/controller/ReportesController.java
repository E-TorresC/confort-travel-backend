package com.conforttravel.api.reportes.controller;

import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.reportes.dto.CheckInReporteResponse;
import com.conforttravel.api.reportes.dto.GananciaPeriodoResponse;
import com.conforttravel.api.reportes.dto.ReporteAgrupadoResponse;
import com.conforttravel.api.reportes.dto.VentaPeriodoResponse;
import com.conforttravel.api.reportes.service.ReportesService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportesController {

    private final ReportesService reportesService;


    /*
     * REP-01
     * Ventas por período
     */
    @GetMapping("/ventas-por-periodo")
    public ResponseEntity<List<VentaPeriodoResponse>> ventasPorPeriodo(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaDesde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaHasta

    ) {

        return ResponseEntity.ok(
                reportesService.ventasPorPeriodo(
                        fechaDesde,
                        fechaHasta
                )
        );
    }


    /*
     * REP-02
     * Ventas por agente
     */
    @GetMapping("/ventas-por-agente")
    public ResponseEntity<List<ReporteAgrupadoResponse>> ventasPorAgente(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaDesde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaHasta

    ) {

        return ResponseEntity.ok(
                reportesService.ventasPorAgente(
                        fechaDesde,
                        fechaHasta
                )
        );
    }


    /*
     * REP-03
     * Ventas por aerolínea
     */
    @GetMapping("/ventas-por-aerolinea")
    public ResponseEntity<List<ReporteAgrupadoResponse>> ventasPorAerolinea(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaDesde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaHasta

    ) {

        return ResponseEntity.ok(
                reportesService.ventasPorAerolinea(
                        fechaDesde,
                        fechaHasta
                )
        );
    }


    /*
     * REP-04
     * Ganancia por período
     */
    @GetMapping("/ganancia-por-periodo")
    public ResponseEntity<GananciaPeriodoResponse> gananciaPorPeriodo(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaDesde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaHasta

    ) {

        return ResponseEntity.ok(
                reportesService.gananciaPorPeriodo(
                        fechaDesde,
                        fechaHasta
                )
        );
    }


    /*
     * REP-05
     * Check-ins pendientes
     */
    @GetMapping("/check-ins/pendientes")
    public ResponseEntity<List<CheckInReporteResponse>> checkInsPendientes() {

        return ResponseEntity.ok(
                reportesService.checkInsPorEstado(
                        EstadoCheckIn.PENDIENTE
                )
        );
    }


    /*
     * REP-06
     * Check-ins notificados
     */
    @GetMapping("/check-ins/notificados")
    public ResponseEntity<List<CheckInReporteResponse>> checkInsNotificados() {

        return ResponseEntity.ok(
                reportesService.checkInsPorEstado(
                        EstadoCheckIn.NOTIFICADO
                )
        );
    }


    /*
     * REP-07
     * Check-ins atendidos
     */
    @GetMapping("/check-ins/atendidos")
    public ResponseEntity<List<CheckInReporteResponse>> checkInsAtendidos() {

        return ResponseEntity.ok(
                reportesService.checkInsPorEstado(
                        EstadoCheckIn.ATENDIDO
                )
        );
    }
}