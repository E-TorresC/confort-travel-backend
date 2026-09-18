package com.conforttravel.api.venta.entity;

import com.conforttravel.api.servicio.entity.ServicioAdicional;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "venta_servicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaServicio {

    @EmbeddedId
    private VentaServicioId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("ventaId")
    @JoinColumn(
            name = "venta_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_venta_servicio_venta")
    )
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("servicioAdicionalId")
    @JoinColumn(
            name = "servicio_adicional_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_venta_servicio_servicio")
    )
    private ServicioAdicional servicioAdicional;
}