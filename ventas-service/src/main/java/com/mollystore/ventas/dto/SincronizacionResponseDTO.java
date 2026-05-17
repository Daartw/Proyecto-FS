package com.mollystore.ventas.dto;

import lombok.Data;

@Data
public class SincronizacionResponseDTO {
    private Long id;
    private Long ventaId;
    private Long itemInventarioId;
    private Integer cantidadDescontada;
    private String estado;
    private String mensajeError;
    private String origen;
}
