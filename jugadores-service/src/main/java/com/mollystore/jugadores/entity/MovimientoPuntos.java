package com.mollystore.jugadores.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimientoPuntos {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "jugador_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Jugador jugador;
    private Integer puntos;
    private String motivo;
    private LocalDateTime fecha;
}
