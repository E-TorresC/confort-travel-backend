package com.conforttravel.api.venta.specification;

import com.conforttravel.api.venta.entity.Venta;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class VentaSpecification {

    private VentaSpecification() {
    }

    public static Specification<Venta> fechaCompraDesde(
            LocalDateTime fechaDesde
    ) {
        return (root, query, criteriaBuilder) -> {

            if (fechaDesde == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get("fechaCompra"),
                    fechaDesde
            );
        };
    }

    public static Specification<Venta> fechaCompraHasta(
            LocalDateTime fechaHasta
    ) {
        return (root, query, criteriaBuilder) -> {

            if (fechaHasta == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(
                    root.get("fechaCompra"),
                    fechaHasta
            );
        };
    }

    public static Specification<Venta> precioFinalMinimo(
            BigDecimal precioMinimo
    ) {
        return (root, query, criteriaBuilder) -> {

            if (precioMinimo == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get("precioFinal"),
                    precioMinimo
            );
        };
    }

    public static Specification<Venta> precioFinalMaximo(
            BigDecimal precioMaximo
    ) {
        return (root, query, criteriaBuilder) -> {

            if (precioMaximo == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(
                    root.get("precioFinal"),
                    precioMaximo
            );
        };
    }

    public static Specification<Venta> clienteIdEquals(
            Long clienteId
    ) {
        return (root, query, criteriaBuilder) -> {

            if (clienteId == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("cliente").get("id"),
                    clienteId
            );
        };
    }

    public static Specification<Venta> agenteIdEquals(
            Long agenteId
    ) {
        return (root, query, criteriaBuilder) -> {

            if (agenteId == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("agente").get("id"),
                    agenteId
            );
        };
    }
}