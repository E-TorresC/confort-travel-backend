package com.conforttravel.api.reportes.repository;

import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.reportes.dto.CheckInReporteResponse;
import com.conforttravel.api.reportes.dto.GananciaPeriodoResponse;
import com.conforttravel.api.reportes.dto.ReporteAgrupadoResponse;
import com.conforttravel.api.reportes.dto.VentaPeriodoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportesRepository extends JpaRepository<
        com.conforttravel.api.venta.entity.Venta,
        Long
        > {

    /*
     * REP-01
     * Ventas por período
     */
    @Query("""
            SELECT new com.conforttravel.api.reportes.dto.VentaPeriodoResponse(
                v.id,
                v.fechaCompra,
                CONCAT(c.nombres, ' ', c.apellidos),
                CONCAT(u.nombre, ' ', u.apellido),
                v.precioInicial,
                v.precioFinal,
                (v.precioFinal - v.precioInicial)
            )
            FROM Venta v
            JOIN v.cliente c
            JOIN v.agente u
            WHERE v.fechaCompra >= :fechaDesde
              AND v.fechaCompra < :fechaHasta
            ORDER BY v.fechaCompra DESC
            """)
    List<VentaPeriodoResponse> obtenerVentasPorPeriodo(
            @Param("fechaDesde") LocalDateTime fechaDesde,
            @Param("fechaHasta") LocalDateTime fechaHasta
    );


    /*
     * REP-02
     * Ventas agrupadas por agente
     */
    @Query("""
            SELECT new com.conforttravel.api.reportes.dto.ReporteAgrupadoResponse(
                u.id,
                CONCAT(u.nombre, ' ', u.apellido),
                COUNT(v.id),
                SUM(v.precioFinal),
                SUM(v.precioFinal - v.precioInicial)
            )
            FROM Venta v
            JOIN v.agente u
            WHERE v.fechaCompra >= :fechaDesde
              AND v.fechaCompra < :fechaHasta
            GROUP BY u.id, u.nombre, u.apellido
            ORDER BY SUM(v.precioFinal) DESC
            """)
    List<ReporteAgrupadoResponse> obtenerVentasPorAgente(
            @Param("fechaDesde") LocalDateTime fechaDesde,
            @Param("fechaHasta") LocalDateTime fechaHasta
    );


    /*
     * REP-04
     * Ganancia acumulada por período
     */
    @Query("""
            SELECT new com.conforttravel.api.reportes.dto.GananciaPeriodoResponse(
                COUNT(v.id),
                COALESCE(SUM(v.precioFinal), 0),
                COALESCE(SUM(v.precioInicial), 0),
                COALESCE(SUM(v.precioFinal - v.precioInicial), 0)
            )
            FROM Venta v
            WHERE v.fechaCompra >= :fechaDesde
              AND v.fechaCompra < :fechaHasta
            """)
    GananciaPeriodoResponse obtenerGananciaPorPeriodo(
            @Param("fechaDesde") LocalDateTime fechaDesde,
            @Param("fechaHasta") LocalDateTime fechaHasta
    );


    /*
     * REP-05, REP-06, REP-07
     * Check-ins por estado
     */
    @Query("""
            SELECT new com.conforttravel.api.reportes.dto.CheckInReporteResponse(
                ci.id,
                ci.estado,
                ci.fechaDisponible,
                ci.fechaNotificacion,
                ci.fechaAtencion,
                t.id,
                t.origen,
                t.destino,
                t.fechaHoraSalida
            )
            FROM CheckIn ci
            JOIN ci.tramo t
            WHERE ci.estado = :estado
            ORDER BY t.fechaHoraSalida ASC
            """)
    List<CheckInReporteResponse> obtenerCheckInsPorEstado(
            @Param("estado") EstadoCheckIn estado
    );


    /*
     * REP-03
     * Ventas relacionadas con aerolíneas.
     *
     * Se utiliza SQL nativo para evitar duplicar el importe de una
     * misma venta cuando posee varios tramos.
     */
    @Query(
            value = """
                    SELECT
                        a.id AS id,
                        a.nombre AS nombre,
                        COUNT(*) AS cantidadVentas,
                        COALESCE(SUM(v.precio_final), 0) AS montoTotal,
                        COALESCE(
                            SUM(v.precio_final - v.precio_inicial),
                            0
                        ) AS gananciaTotal
                    FROM (
                        SELECT DISTINCT
                            v.id AS venta_id,
                            a.id AS aerolinea_id
                        FROM venta v
                        INNER JOIN viaje vi
                            ON vi.venta_id = v.id
                        INNER JOIN tramo t
                            ON t.viaje_id = vi.id
                        INNER JOIN reserva r
                            ON r.tramo_id = t.id
                        INNER JOIN aerolinea a
                            ON a.id = r.aerolinea_id
                        WHERE v.fecha_compra >= :fechaDesde
                          AND v.fecha_compra < :fechaHasta
                    ) x
                    INNER JOIN venta v
                        ON v.id = x.venta_id
                    INNER JOIN aerolinea a
                        ON a.id = x.aerolinea_id
                    GROUP BY
                        a.id,
                        a.nombre
                    ORDER BY
                        SUM(v.precio_final) DESC
                    """,
            nativeQuery = true
    )
    List<Object[]> obtenerVentasPorAerolinea(
            @Param("fechaDesde") LocalDateTime fechaDesde,
            @Param("fechaHasta") LocalDateTime fechaHasta
    );
}