package com.mollystore.inventario.dto;

import com.mollystore.inventario.entity.EstadoConservacion;
import com.mollystore.inventario.entity.TipoProducto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemInventarioResponseDTO {
    private Long id;
    private Long cartaId;
    private EstadoConservacion estado;
    private TipoProducto tipoProducto;
    private Integer cantidad;
    private String ubicacion;
    private LocalDateTime fechaIngreso;
}
