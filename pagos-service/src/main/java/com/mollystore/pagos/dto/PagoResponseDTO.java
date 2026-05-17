package com.mollystore.pagos.dto;

import com.mollystore.pagos.entity.EstadoPago;
import com.mollystore.pagos.entity.MetodoPago;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class PagoResponseDTO {
    private Long id;
    private Long ventaId;
    private Double monto;
    private String moneda;
    private MetodoPago metodoPago;
    private EstadoPago estado;
    private String codigoAutorizacion;
    private String referenciaPasarela;
    private LocalDateTime fechaTransaccion;
}
