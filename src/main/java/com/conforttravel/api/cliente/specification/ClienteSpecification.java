package com.conforttravel.api.cliente.specification;

import com.conforttravel.api.cliente.entity.Cliente;
import org.springframework.data.jpa.domain.Specification;

public final class ClienteSpecification {

    private ClienteSpecification() {
    }

    public static Specification<Cliente> nombresContains(String nombres) {
        return (root, query, criteriaBuilder) -> {

            if (nombres == null || nombres.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nombres")),
                    "%" + nombres.trim().toLowerCase() + "%"
            );
        };
    }

    public static Specification<Cliente> apellidosContains(String apellidos) {
        return (root, query, criteriaBuilder) -> {

            if (apellidos == null || apellidos.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("apellidos")),
                    "%" + apellidos.trim().toLowerCase() + "%"
            );
        };
    }

    public static Specification<Cliente> telefonoContains(String telefono) {
        return (root, query, criteriaBuilder) -> {

            if (telefono == null || telefono.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    root.get("telefono"),
                    "%" + telefono.trim() + "%"
            );
        };
    }
}