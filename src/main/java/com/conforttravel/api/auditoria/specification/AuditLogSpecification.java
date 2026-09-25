package com.conforttravel.api.auditoria.specification;

import com.conforttravel.api.auditoria.entity.AuditLog;
import org.springframework.data.jpa.domain.Specification;
import java.time.OffsetDateTime;

public final class AuditLogSpecification {

    private AuditLogSpecification() {
    }

    public static Specification<AuditLog> usuarioIdEquals(Long usuarioId) {
        return (root, query, criteriaBuilder) -> {
            if (usuarioId == null) return null;
            return criteriaBuilder.equal(root.get("usuario").get("id"), usuarioId);
        };
    }

    public static Specification<AuditLog> accionEquals(String accion) {
        return (root, query, criteriaBuilder) -> {
            if (accion == null || accion.isBlank()) return null;
            return criteriaBuilder.equal(
                    criteriaBuilder.upper(root.get("accion")),
                    accion.trim().toUpperCase()
            );
        };
    }

    public static Specification<AuditLog> entidadEquals(String entidad) {
        return (root, query, criteriaBuilder) -> {
            if (entidad == null || entidad.isBlank()) return null;
            return criteriaBuilder.equal(
                    criteriaBuilder.upper(root.get("entidad")),
                    entidad.trim().toUpperCase()
            );
        };
    }

    public static Specification<AuditLog> fechaDesde(OffsetDateTime fechaDesde) {
        return (root, query, criteriaBuilder) -> {
            if (fechaDesde == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), fechaDesde);
        };
    }

    public static Specification<AuditLog> fechaHasta(OffsetDateTime fechaHasta) {
        return (root, query, criteriaBuilder) -> {
            if (fechaHasta == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), fechaHasta);
        };
    }
}