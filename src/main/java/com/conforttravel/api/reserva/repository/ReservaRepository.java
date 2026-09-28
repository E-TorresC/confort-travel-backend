package com.conforttravel.api.reserva.repository;

import com.conforttravel.api.reserva.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findByTramoId(Long tramoId);
}