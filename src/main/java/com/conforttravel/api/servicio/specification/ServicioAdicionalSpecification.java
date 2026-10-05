package com.conforttravel.api.servicio.specification;

import com.conforttravel.api.servicio.entity.ServicioAdicional;
import org.springframework.data.jpa.domain.Specification;

public final class ServicioAdicionalSpecification {

    private ServicioAdicionalSpecification() {
    }

    public static Specification<ServicioAdicional> nombreContains(String nombre) {
        return (root, query, criteriaBuilder) -> {
            if (nombre == null || nombre.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nombre")),
                    "%" + nombre.trim().toLowerCase() + "%"
            );
        };
    }

    public static Specification<ServicioAdicional> activoEquals(Boolean activo) {
        return (root, query, criteriaBuilder) -> {
            if (activo == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("activo"), activo);
        };
    }
}