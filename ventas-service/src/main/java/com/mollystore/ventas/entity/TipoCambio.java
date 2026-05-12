package com.mollystore.ventas.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoCambio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String monedaOrigen;
    private String monedaDestino;
    private Double valorCambio;
    private LocalDateTime fechaActualizacion;
}
