package com.mollystore.ventas.controller;
import com.mollystore.ventas.entity.*;
import com.mollystore.ventas.service.VentaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController @RequestMapping("/api/ventas") @RequiredArgsConstructor
public class VentaController {
    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(ventaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.findById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> porCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ventaService.findByCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<Venta> crear(@Valid @RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.crear(venta));
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<Venta> completar(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.completar(id));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<Venta> anular(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.anular(id));
    }

    @GetMapping("/convertir")
    public ResponseEntity<Map<String, Double>> convertir(@RequestParam Double monto, @RequestParam String moneda) {
        return ResponseEntity.ok(Map.of("resultado", ventaService.convertir(monto, moneda)));
    }
}
