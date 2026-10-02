package com.conforttravel.api.tramo.entity;

import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.reserva.entity.Reserva;
import com.conforttravel.api.venta.entity.Venta;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "tramo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, updatable = false, length = 20)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tramo", nullable = false, length = 20)
    private TipoTramo tipoTramo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_viaje", nullable = false, length = 20)
    private TipoViaje tipoViaje;

    @Column(nullable = false, length = 150)
    private String origen;

    @Column(nullable = false, length = 150)
    private String destino;

    @Column(
            name = "fecha_hora_salida",
            nullable = false
    )
    private OffsetDateTime fechaHoraSalida;

    @Column(name = "fecha_hora_llegada")
    private OffsetDateTime fechaHoraLlegada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "venta_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_tramo_venta")
    )
    private Venta venta;

    @OneToOne(
            mappedBy = "tramo",
            fetch = FetchType.LAZY
    )
    private Reserva reserva;

    @OneToOne(
            mappedBy = "tramo",
            fetch = FetchType.LAZY
    )
    private CheckIn checkIn;
}