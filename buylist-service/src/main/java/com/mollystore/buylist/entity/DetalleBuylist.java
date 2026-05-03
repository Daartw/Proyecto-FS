package com.mollystore.buylist.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleBuylist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "solicitud_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private SolicitudBuylist solicitud;
    private Long cartaId;
    @Enumerated(EnumType.STRING) private EstadoConservacion estadoDeclarado;
    @Enumerated(EnumType.STRING) private EstadoConservacion estadoVerificado;
    private Integer cantidad;
    private Double valorUnitarioOfrecido;
    private Boolean autenticidadConfirmada;
}
