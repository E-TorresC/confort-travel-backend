package com.conforttravel.api.auditoria.specification;

import com.conforttravel.api.auditoria.dto.AuditLogFilterRequest;
import com.conforttravel.api.auditoria.entity.AuditLog;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuditLogSpecificationBuilder {

    public Specification<AuditLog> build(AuditLogFilterRequest filter) {
        Specification<AuditLog> specification = Specification.allOf();

        if (filter == null) {
            return specification;
        }

        specification = specification.and(AuditLogSpecification.usuarioIdEquals(filter.usuarioId()));
        specification = specification.and(AuditLogSpecification.accionEquals(filter.accion()));
        specification = specification.and(AuditLogSpecification.entidadEquals(filter.entidad()));
        specification = specification.and(AuditLogSpecification.fechaDesde(filter.fechaDesde()));
        specification = specification.and(AuditLogSpecification.fechaHasta(filter.fechaHasta()));

        return specification;
    }
}