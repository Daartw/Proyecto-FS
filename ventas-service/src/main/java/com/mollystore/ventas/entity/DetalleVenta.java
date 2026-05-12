package com.mollystore.ventas.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleVenta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "venta_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Venta venta;
    private Long itemInventarioId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
