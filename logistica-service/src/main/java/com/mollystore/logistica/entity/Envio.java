package com.mollystore.logistica.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Envio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ventaId;
    private Long clienteId;
    @NotBlank private String direccionDestino;
    private String ciudad;
    private String region;
    @Enumerated(EnumType.STRING) private EstadoEnvio estado;
    private String codigoSeguimiento;
    private String transportista;
    private LocalDateTime fechaDespacho;
    private LocalDateTime fechaEntregaEstimada;
    private LocalDateTime fechaEntregaReal;
    @Enumerated(EnumType.STRING) private TipoEmpaque tipoEmpaque;
}
