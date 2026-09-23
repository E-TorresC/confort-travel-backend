package com.conforttravel.api.notificacion.repository;

import com.conforttravel.api.notificacion.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository
        extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByFechaLecturaIsNullOrderByFechaCreacionDesc();
}