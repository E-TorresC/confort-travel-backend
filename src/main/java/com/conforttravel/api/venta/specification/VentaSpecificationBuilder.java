package com.conforttravel.api.venta.specification;

import com.conforttravel.api.venta.dto.VentaFilterRequest;
import com.conforttravel.api.venta.entity.Venta;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VentaSpecificationBuilder {

    public Specification<Venta> build(
            VentaFilterRequest filter
    ) {

        Specification<Venta> specification =
                Specification.allOf();

        if (filter == null) {
            return specification;
        }

        specification = specification.and(
                VentaSpecification.fechaCompraDesde(
                        filter.fechaDesde()
                )
        );

        specification = specification.and(
                VentaSpecification.fechaCompraHasta(
                        filter.fechaHasta()
                )
        );

        specification = specification.and(
                VentaSpecification.precioFinalMinimo(
                        filter.precioMinimo()
                )
        );

        specification = specification.and(
                VentaSpecification.precioFinalMaximo(
                        filter.precioMaximo()
                )
        );

        specification = specification.and(
                VentaSpecification.clienteIdEquals(
                        filter.clienteId()
                )
        );

        specification = specification.and(
                VentaSpecification.agenteIdEquals(
                        filter.agenteId()
                )
        );

        return specification;
    }
}