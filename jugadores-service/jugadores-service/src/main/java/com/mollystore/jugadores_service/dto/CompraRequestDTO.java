package com.mollystore.jugadores_service.dto;

import com.mollystore.jugadores_service.model.HistorialCompra.TipoCompra;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CompraRequestDTO {

    @NotNull(message = "El jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @NotNull(message = "El tipo de compra es obligatorio")
    private TipoCompra tipoCompra;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto no puede ser negativo")
    private BigDecimal monto;
}
