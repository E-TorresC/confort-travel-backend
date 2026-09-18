package com.conforttravel.api.venta.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VentaServicioId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "venta_id", nullable = false)
    private Long ventaId;

    @Column(name = "servicio_adicional_id", nullable = false)
    private Long servicioAdicionalId;
}