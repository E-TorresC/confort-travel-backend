package com.conforttravel.api.aerolinea.specification;

import com.conforttravel.api.aerolinea.dto.AerolineaFilterRequest;
import com.conforttravel.api.aerolinea.entity.Aerolinea;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AerolineaSpecificationBuilder {

    public Specification<Aerolinea> build(
            AerolineaFilterRequest filter
    ) {

        Specification<Aerolinea> specification =
                Specification.allOf();

        if (filter == null) {
            return specification;
        }

        specification = specification.and(
                AerolineaSpecification.nombreContains(
                        filter.nombre()
                )
        );

        specification = specification.and(
                AerolineaSpecification.codigoContains(
                        filter.codigo()
                )
        );

        specification = specification.and(
                AerolineaSpecification.activoEquals(
                        filter.activo()
                )
        );

        return specification;
    }
}