package com.mollystore.ventas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SincronizacionRequestDTO {
    private Long ventaId;
    private Long itemInventarioId;
    private Integer cantidadDescontada;
    private String origen;
}
