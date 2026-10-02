package com.conforttravel.api.tramo.repository;

import com.conforttravel.api.tramo.entity.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface TramoRepository extends JpaRepository<Tramo, Long> {
    List<Tramo> findByVentaId(Long ventaId);
    List<Tramo> findTop5ByFechaHoraSalidaGreaterThanEqualOrderByFechaHoraSalidaAsc(OffsetDateTime fecha);
    Optional<Tramo> findTopByOrderByIdDesc();
}