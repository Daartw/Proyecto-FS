package com.mollystore.reabastecimiento.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrdenReabastecimiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "distribuidor_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Distribuidor distribuidor;
    private LocalDateTime fechaOrden;
    private LocalDateTime fechaEstimadaEntrega;
    @Enumerated(EnumType.STRING) private EstadoOrden estado;
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<DetalleOrden> detalles;
}
