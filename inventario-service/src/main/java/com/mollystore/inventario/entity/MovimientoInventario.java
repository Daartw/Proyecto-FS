package com.mollystore.inventario.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimientoInventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "item_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private ItemInventario item;
    private Integer cantidadMovida;
    private String motivo;
    private LocalDateTime fecha;
}
