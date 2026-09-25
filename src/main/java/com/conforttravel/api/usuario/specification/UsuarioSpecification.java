package com.conforttravel.api.usuario.specification;

import com.conforttravel.api.usuario.entity.Usuario;
import org.springframework.data.jpa.domain.Specification;

public final class UsuarioSpecification {

    private UsuarioSpecification() {
    }

    public static Specification<Usuario> nombreContains(String nombre) {
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

    public static Specification<Usuario> apellidoContains(String apellido) {
        return (root, query, criteriaBuilder) -> {
            if (apellido == null || apellido.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("apellido")),
                    "%" + apellido.trim().toLowerCase() + "%"
            );
        };
    }

    public static Specification<Usuario> emailContains(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isBlank()) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("email")),
                    "%" + email.trim().toLowerCase() + "%"
            );
        };
    }

    public static Specification<Usuario> activoEquals(Boolean activo) {
        return (root, query, criteriaBuilder) -> {
            if (activo == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("activo"), activo);
        };
    }
}