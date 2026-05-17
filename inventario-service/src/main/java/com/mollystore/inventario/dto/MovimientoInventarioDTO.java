package com.mollystore.inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimientoInventarioDTO {
    private Long id;
    @NotNull private Long itemId;
    @NotNull private Integer cantidadMovida;
    @NotBlank private String motivo;
    private LocalDateTime fecha;
}
