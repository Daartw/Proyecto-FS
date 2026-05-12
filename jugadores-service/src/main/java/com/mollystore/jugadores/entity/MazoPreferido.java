package com.mollystore.jugadores.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MazoPreferido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "jugador_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Jugador jugador;
    private String nombreMazo;
    private String arquetipo;
    private String formato;
}
