package com.mollystore.sincronizacion.dto;

import lombok.Data;

@Data
public class InventarioResponseDTO {
    private Long id;
    private Long cartaId;
    private Integer cantidad;
    private String ubicacion;
    private String estado;
    private String tipoProducto;
}
