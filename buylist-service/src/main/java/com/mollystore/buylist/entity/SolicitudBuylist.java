package com.mollystore.buylist.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SolicitudBuylist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private LocalDateTime fechaSolicitud;
    @Enumerated(EnumType.STRING) private EstadoBuylist estado;
    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<DetalleBuylist> detalles;
    private Double creditoGenerado;
}
