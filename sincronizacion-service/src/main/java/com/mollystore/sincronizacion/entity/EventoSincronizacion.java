package com.mollystore.sincronizacion.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EventoSincronizacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ventaId;
    private Long itemInventarioId;
    private Integer cantidadDescontada;
    private LocalDateTime fechaEvento;
    @Enumerated(EnumType.STRING) private EstadoSincronizacion estado;
    private String mensajeError;
    private String origen;
}
