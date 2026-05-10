package com.mollystore.jugadores_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_compras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    // Referencia al Catalogo (otro microservicio, no FK directa)
    @NotNull(message = "El producto es obligatorio")
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(name = "nombre_producto")
    private String nombreProducto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_compra", nullable = false)
    private TipoCompra tipoCompra;

    @NotNull
    @DecimalMin(value = "0.0", message = "El monto no puede ser negativo")
    @Column(nullable = false)
    private BigDecimal monto;

    @Column(name = "puntos_generados")
    private Integer puntosGenerados;

    @Column(name = "fecha_compra")
    @Builder.Default
    private LocalDateTime fechaCompra = LocalDateTime.now();

    public enum TipoCompra {
        SOBRE,
        TORNEO,
        MAZO,
        CARTA_INDIVIDUAL
    }
}
