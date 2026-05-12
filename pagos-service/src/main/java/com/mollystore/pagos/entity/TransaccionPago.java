package com.mollystore.pagos.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransaccionPago {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ventaId;
    private Double monto;
    private String moneda;
    @Enumerated(EnumType.STRING) private MetodoPago metodoPago;
    @Enumerated(EnumType.STRING) private EstadoPago estado;
    private String codigoAutorizacion;
    private String referenciaPasarela;
    private LocalDateTime fechaTransaccion;
}
