package com.conforttravel.api.viaje.entity;

import com.conforttravel.api.tramo.entity.Tramo;
import com.conforttravel.api.venta.entity.Venta;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "viaje",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_viaje_venta",
                        columnNames = "venta_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "venta_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_viaje_venta")
    )
    private Venta venta;

    @OneToMany(
            mappedBy = "viaje",
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Tramo> tramos = new ArrayList<>();
}