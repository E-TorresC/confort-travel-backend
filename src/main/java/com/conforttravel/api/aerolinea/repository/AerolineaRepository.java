package com.conforttravel.api.aerolinea.repository;

import com.conforttravel.api.aerolinea.entity.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AerolineaRepository extends JpaRepository<Aerolinea, Long>, JpaSpecificationExecutor<Aerolinea> {
}