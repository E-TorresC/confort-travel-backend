package com.conforttravel.api.venta.repository;

import com.conforttravel.api.venta.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long>, JpaSpecificationExecutor<Venta> {

    @Query("SELECT SUM(v.precioFinal) FROM Venta v")
    BigDecimal sumMontoTotalVendido();

    @Query("SELECT SUM(v.precioFinal - v.precioInicial) FROM Venta v")
    BigDecimal sumGananciaAcumulada();

    Optional<Venta> findTopByOrderByIdDesc();
}