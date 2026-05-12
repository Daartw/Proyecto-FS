package com.mollystore.ventas.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Venta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private Long vendedorId;
    private LocalDateTime fechaVenta;
    @Enumerated(EnumType.STRING) private Moneda moneda;
    private Double totalMonedaOrigen;
    private Double totalCLP;
    private Double tipoCambioUsado;
    @Enumerated(EnumType.STRING) private EstadoVenta estado;
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<DetalleVenta> detalles;
}
