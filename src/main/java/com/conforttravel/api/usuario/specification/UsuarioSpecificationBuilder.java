package com.conforttravel.api.usuario.specification;

import com.conforttravel.api.usuario.dto.UsuarioFilterRequest;
import com.conforttravel.api.usuario.entity.Usuario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSpecificationBuilder {

    public Specification<Usuario> build(UsuarioFilterRequest filter) {

        Specification<Usuario> specification = Specification.allOf();

        if (filter == null) {
            return specification;
        }

        specification = specification.and(
                UsuarioSpecification.nombreContains(filter.nombre())
        );

        specification = specification.and(
                UsuarioSpecification.apellidoContains(filter.apellido())
        );

        specification = specification.and(
                UsuarioSpecification.emailContains(filter.email())
        );

        specification = specification.and(
                UsuarioSpecification.activoEquals(filter.activo())
        );

        return specification;
    }
}