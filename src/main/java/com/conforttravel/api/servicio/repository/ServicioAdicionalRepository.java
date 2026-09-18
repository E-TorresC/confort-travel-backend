package com.conforttravel.api.servicio.repository;

import com.conforttravel.api.servicio.entity.ServicioAdicional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioAdicionalRepository
        extends JpaRepository<ServicioAdicional, Long> {
}