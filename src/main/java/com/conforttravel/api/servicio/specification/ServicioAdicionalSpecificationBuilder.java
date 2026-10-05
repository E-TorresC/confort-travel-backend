package com.conforttravel.api.servicio.specification;

import com.conforttravel.api.servicio.dto.ServicioAdicionalFilterRequest;
import com.conforttravel.api.servicio.entity.ServicioAdicional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ServicioAdicionalSpecificationBuilder {

    public Specification<ServicioAdicional> build(ServicioAdicionalFilterRequest filter) {
        Specification<ServicioAdicional> specification = Specification.allOf();

        if (filter == null) {
            return specification;
        }

        specification = specification.and(ServicioAdicionalSpecification.nombreContains(filter.nombre()));
        specification = specification.and(ServicioAdicionalSpecification.activoEquals(filter.activo()));

        return specification;
    }
}