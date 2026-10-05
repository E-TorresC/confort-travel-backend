package com.conforttravel.api.servicio.repository;

import com.conforttravel.api.servicio.entity.ServicioAdicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServicioAdicionalRepository extends JpaRepository<ServicioAdicional, Long>, JpaSpecificationExecutor<ServicioAdicional> {
}