package com.conforttravel.api.viaje.repository;

import com.conforttravel.api.viaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
}