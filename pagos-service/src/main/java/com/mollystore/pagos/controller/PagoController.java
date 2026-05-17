package com.mollystore.pagos.controller;

import com.mollystore.pagos.dto.*;
import com.mollystore.pagos.entity.*;
import com.mollystore.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController @RequestMapping("/api/pagos") @RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    @PostMapping("/iniciar")
    public ResponseEntity<PagoResponseDTO> iniciar(@Valid @RequestBody PagoRequestDTO dto) {
        log.info("POST /api/pagos/iniciar - ventaId={}, metodo={}, monto={}", dto.getVentaId(), dto.getMetodoPago(), dto.getMonto());
        TransaccionPago t = new TransaccionPago();
        t.setVentaId(dto.getVentaId());
        t.setMonto(dto.getMonto());
        t.setMoneda(dto.getMoneda());
        t.setMetodoPago(dto.getMetodoPago());
        return ResponseEntity.ok(toResponse(pagoService.iniciar(t)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> detalle(@PathVariable Long id) {
        log.info("GET /api/pagos/{} - obteniendo detalle de transaccion", id);
        return ResponseEntity.ok(toResponse(pagoService.findById(id)));
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<PagoResponseDTO>> porVenta(@PathVariable Long ventaId) {
        log.info("GET /api/pagos/venta/{} - obteniendo pagos por venta", ventaId);
        return ResponseEntity.ok(pagoService.findByVenta(ventaId).stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<PagoResponseDTO> confirmar(@PathVariable Long id) {
        log.info("POST /api/pagos/{}/confirmar - confirmando pago", id);
        return ResponseEntity.ok(toResponse(pagoService.confirmar(id)));
    }

    @PostMapping("/{id}/reembolsar")
    public ResponseEntity<PagoResponseDTO> reembolsar(@PathVariable Long id) {
        log.info("POST /api/pagos/{}/reembolsar - procesando reembolso", id);
        return ResponseEntity.ok(toResponse(pagoService.reembolsar(id)));
    }

    @GetMapping("/metodos")
    public ResponseEntity<List<MetodoPago>> metodos() {
        log.info("GET /api/pagos/metodos - listando metodos de pago disponibles");
        return ResponseEntity.ok(Arrays.asList(MetodoPago.values()));
    }

    private PagoResponseDTO toResponse(TransaccionPago t) {
        return PagoResponseDTO.builder()
            .id(t.getId())
            .ventaId(t.getVentaId())
            .monto(t.getMonto())
            .moneda(t.getMoneda())
            .metodoPago(t.getMetodoPago())
            .estado(t.getEstado())
            .codigoAutorizacion(t.getCodigoAutorizacion())
            .referenciaPasarela(t.getReferenciaPasarela())
            .fechaTransaccion(t.getFechaTransaccion())
            .build();
    }
}
