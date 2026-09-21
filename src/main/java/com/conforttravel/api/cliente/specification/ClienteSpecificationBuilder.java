package com.conforttravel.api.cliente.specification;

import com.conforttravel.api.cliente.dto.ClienteFilterRequest;
import com.conforttravel.api.cliente.entity.Cliente;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ClienteSpecificationBuilder {

    public Specification<Cliente> build(ClienteFilterRequest filter) {

        Specification<Cliente> specification = Specification.allOf();

        if (filter == null) {
            return specification;
        }

        specification = specification.and(
                ClienteSpecification.nombresContains(filter.nombres())
        );

        specification = specification.and(
                ClienteSpecification.apellidosContains(filter.apellidos())
        );

        specification = specification.and(
                ClienteSpecification.telefonoContains(filter.telefono())
        );

        return specification;
    }
}