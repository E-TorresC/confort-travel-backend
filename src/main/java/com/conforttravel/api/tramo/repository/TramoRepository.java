package com.conforttravel.api.tramo.repository;

import com.conforttravel.api.tramo.entity.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TramoRepository extends JpaRepository<Tramo, Long> {

    List<Tramo> findByViajeId(Long viajeId);
}