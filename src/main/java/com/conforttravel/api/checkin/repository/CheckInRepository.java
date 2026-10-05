package com.conforttravel.api.checkin.repository;

import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    Optional<CheckIn> findByTramoId(Long tramoId);

    boolean existsByTramoId(Long tramoId);

    List<CheckIn> findByEstado(EstadoCheckIn estado);

    Page<CheckIn> findByEstado(EstadoCheckIn estado, Pageable pageable);

    @Query("SELECT c FROM CheckIn c " +
            "WHERE (:estado IS NULL OR c.estado = :estado) " +
            "ORDER BY CASE c.estado " +
            "WHEN com.conforttravel.api.checkin.entity.EstadoCheckIn.NOTIFICADO THEN 1 " +
            "WHEN com.conforttravel.api.checkin.entity.EstadoCheckIn.PENDIENTE THEN 2 " +
            "WHEN com.conforttravel.api.checkin.entity.EstadoCheckIn.ATENDIDO THEN 3 " +
            "ELSE 4 END ASC, c.fechaDisponible ASC")
    Page<CheckIn> findByEstadoConPrioridad(@Param("estado") EstadoCheckIn estado, Pageable pageable);

    List<CheckIn> findByEstadoAndFechaDisponibleLessThanEqual(EstadoCheckIn estado, OffsetDateTime fecha);

    long countByEstado(EstadoCheckIn estado);

    Optional<CheckIn> findTopByOrderByIdDesc();
}