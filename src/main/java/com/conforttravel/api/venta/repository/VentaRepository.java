package com.conforttravel.api.venta.repository;

import com.conforttravel.api.venta.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}