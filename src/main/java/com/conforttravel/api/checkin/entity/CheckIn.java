package com.conforttravel.api.checkin.entity;

import com.conforttravel.api.tramo.entity.Tramo;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "check_in",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_check_in_tramo",
                        columnNames = "tramo_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCheckIn estado;

    @Column(
            name = "fecha_disponible",
            nullable = false
    )
    private OffsetDateTime fechaDisponible;

    @Column(name = "fecha_notificacion")
    private OffsetDateTime fechaNotificacion;

    @Column(name = "fecha_atencion")
    private OffsetDateTime fechaAtencion;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tramo_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_check_in_tramo")
    )
    private Tramo tramo;
}