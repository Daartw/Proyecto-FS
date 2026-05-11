package com.mollystore.inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemInventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cartaId;
    @Enumerated(EnumType.STRING) private EstadoConservacion estado;
    @Enumerated(EnumType.STRING) private TipoProducto tipoProducto;
    @NotNull @PositiveOrZero private Integer cantidad;
    private String ubicacion;
    private LocalDateTime fechaIngreso;
}
