package com.conforttravel.api.reserva.entity;

import com.conforttravel.api.aerolinea.entity.Aerolinea;
import com.conforttravel.api.tramo.entity.Tramo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "reserva",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reserva_tramo",
                        columnNames = "tramo_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "codigo_reserva",
            nullable = false,
            length = 50
    )
    private String codigoReserva;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tramo_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_reserva_tramo")
    )
    private Tramo tramo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "aerolinea_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_reserva_aerolinea")
    )
    private Aerolinea aerolinea;
}