package com.mollystore.alertas.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SuscripcionCarta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jugadorId;
    private Long cartaId;
    private LocalDateTime fechaSuscripcion;
    private Boolean activa;
}
