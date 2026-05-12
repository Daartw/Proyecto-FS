package com.mollystore.reabastecimiento.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleOrden {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "orden_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private OrdenReabastecimiento orden;
    private Long cartaId;
    private Integer cantidadSolicitada;
    private Integer cantidadRecibida;
    private Double precioUnitario;
}
