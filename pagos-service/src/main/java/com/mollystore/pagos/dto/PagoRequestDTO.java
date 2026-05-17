package com.mollystore.pagos.dto;

import com.mollystore.pagos.entity.MetodoPago;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PagoRequestDTO {
    @NotNull private Long ventaId;
    @NotNull @Positive private Double monto;
    private String moneda;
    @NotNull private MetodoPago metodoPago;
}
