package com.mollystore.alertas.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Alerta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long destinatarioId;
    private String emailDestino;
    @Enumerated(EnumType.STRING) private TipoAlerta tipo;
    @Enumerated(EnumType.STRING) private CanalEnvio canal;
    private String asunto;
    private String mensaje;
    @Enumerated(EnumType.STRING) private EstadoAlerta estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvio;
}
