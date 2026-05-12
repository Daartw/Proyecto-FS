package com.mollystore.pagos.controller;
import com.mollystore.pagos.entity.*;
import com.mollystore.pagos.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController @RequestMapping("/api/pagos") @RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    @PostMapping("/iniciar") public ResponseEntity<TransaccionPago> iniciar(@RequestBody TransaccionPago t) {
        return ResponseEntity.ok(pagoService.iniciar(t));
    }
    @GetMapping("/{id}") public ResponseEntity<TransaccionPago> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.findById(id));
    }
    @GetMapping("/venta/{ventaId}") public List<TransaccionPago> porVenta(@PathVariable Long ventaId) {
        return pagoService.findByVenta(ventaId);
    }
    @PostMapping("/{id}/confirmar") public ResponseEntity<TransaccionPago> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.confirmar(id));
    }
    @PostMapping("/{id}/reembolsar") public ResponseEntity<TransaccionPago> reembolsar(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.reembolsar(id));
    }
    @GetMapping("/metodos") public List<MetodoPago> metodos() { return Arrays.asList(MetodoPago.values()); }
}
