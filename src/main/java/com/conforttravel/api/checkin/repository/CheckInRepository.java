package com.conforttravel.api.checkin.repository;

import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    Optional<CheckIn> findByTramoId(Long tramoId);

    boolean existsByTramoId(Long tramoId);

    List<CheckIn> findByEstado(
            EstadoCheckIn estado
    );

    List<CheckIn> findByEstadoAndFechaDisponibleLessThanEqual(
            EstadoCheckIn estado,
            OffsetDateTime fecha
    );
}