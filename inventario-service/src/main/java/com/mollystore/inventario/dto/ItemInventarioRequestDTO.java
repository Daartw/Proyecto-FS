package com.mollystore.inventario.dto;

import com.mollystore.inventario.entity.EstadoConservacion;
import com.mollystore.inventario.entity.TipoProducto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ItemInventarioRequestDTO {
    @NotNull private Long cartaId;
    private EstadoConservacion estado;
    private TipoProducto tipoProducto;
    @NotNull @PositiveOrZero private Integer cantidad;
    private String ubicacion;
}
