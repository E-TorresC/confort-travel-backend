package com.conforttravel.api.venta.entity;

import com.conforttravel.api.cliente.entity.Cliente;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.viaje.entity.Viaje;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "fecha_compra",
            nullable = false
    )
    private OffsetDateTime fechaCompra;

    @Column(
            name = "precio_inicial",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal precioInicial;

    @Column(
            name = "precio_final",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal precioFinal;

    @Column(length = 1000)
    private String observaciones;

    @Column(nullable = false)
    private Integer adultos;

    @Column(nullable = false)
    private Integer infantes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "cliente_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_venta_cliente")
    )
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "agente_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_venta_agente")
    )
    private Usuario agente;

    @OneToOne(
            mappedBy = "venta",
            fetch = FetchType.LAZY
    )
    private Viaje viaje;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Version
    @Column(nullable = false)
    @Builder.Default
    private Long version = 0L;

    @Transient
    public BigDecimal getGanancia() {
        if (precioInicial == null || precioFinal == null) {
            return null;
        }

        return precioFinal.subtract(precioInicial);
    }

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        if (fechaCompra == null) {
            fechaCompra = now;
        }

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
}