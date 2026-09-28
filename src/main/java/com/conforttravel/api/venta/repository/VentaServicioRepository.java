package com.conforttravel.api.venta.repository;

import com.conforttravel.api.venta.entity.VentaServicio;
import com.conforttravel.api.venta.entity.VentaServicioId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaServicioRepository extends JpaRepository<VentaServicio, VentaServicioId> {

    List<VentaServicio> findByVentaId(Long ventaId);
}