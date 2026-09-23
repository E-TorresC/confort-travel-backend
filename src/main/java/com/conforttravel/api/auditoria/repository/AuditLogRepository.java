package com.conforttravel.api.auditoria.repository;

import com.conforttravel.api.auditoria.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    Page<AuditLog> findByUsuarioId(
            Long usuarioId,
            Pageable pageable
    );

    Page<AuditLog> findByEntidadIgnoreCase(
            String entidad,
            Pageable pageable
    );

    Page<AuditLog> findByAccionIgnoreCase(
            String accion,
            Pageable pageable
    );
}