package com.conforttravel.api.notificacion.repository;

import com.conforttravel.api.notificacion.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository
        extends JpaRepository<Notificacion, Long> {
}