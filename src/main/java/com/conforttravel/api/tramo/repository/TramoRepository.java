package com.conforttravel.api.tramo.repository;

import com.conforttravel.api.tramo.entity.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface TramoRepository extends JpaRepository<Tramo, Long> {

    List<Tramo> findByViajeId(Long viajeId);

    // Método para traer los siguientes 5 viajes (tramos) que aún no salen
    List<Tramo> findTop5ByFechaHoraSalidaGreaterThanEqualOrderByFechaHoraSalidaAsc(OffsetDateTime fecha);
}