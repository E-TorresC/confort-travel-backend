package com.conforttravel.api.aerolinea.specification;

import com.conforttravel.api.aerolinea.entity.Aerolinea;
import org.springframework.data.jpa.domain.Specification;

public final class AerolineaSpecification {

    private AerolineaSpecification() {
    }

    public static Specification<Aerolinea> nombreContains(String nombre) {
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

    public static Specification<Aerolinea> codigoContains(String codigo) {
        return (root, query, criteriaBuilder) -> {

            if (codigo == null || codigo.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("codigo")),
                    "%" + codigo.trim().toLowerCase() + "%"
            );
        };
    }

    public static Specification<Aerolinea> activoEquals(Boolean activo) {
        return (root, query, criteriaBuilder) -> {

            if (activo == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("activo"),
                    activo
            );
        };
    }
}