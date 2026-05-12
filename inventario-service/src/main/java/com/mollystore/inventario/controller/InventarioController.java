package com.mollystore.inventario.controller;

import com.mollystore.inventario.entity.*;
import com.mollystore.inventario.service.InventarioService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {
    private final InventarioService inventarioService;

    @GetMapping
    public List<ItemInventario> listar() { return inventarioService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ItemInventario> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.findById(id));
    }

    @GetMapping("/carta/{cartaId}")
    public List<ItemInventario> porCarta(@PathVariable Long cartaId) {
        return inventarioService.findByCartaId(cartaId);
    }

    @GetMapping("/bajo-stock")
    public List<ItemInventario> bajoStock() { return inventarioService.findBajoStock(); }

    @PostMapping
    public ResponseEntity<ItemInventario> agregar(@RequestBody ItemInventario item) {
        return ResponseEntity.ok(inventarioService.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemInventario> actualizar(@PathVariable Long id, @RequestBody ItemInventario item) {
        return ResponseEntity.ok(inventarioService.update(id, item));
    }

    @PatchMapping("/{id}/descontar")
    public ResponseEntity<Void> descontar(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        inventarioService.descontarStock(id, body.get("cantidad"));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movimientos")
    public List<MovimientoInventario> movimientos() { return inventarioService.findAllMovimientos(); }

    @PostMapping("/movimientos")
    public ResponseEntity<MovimientoInventario> registrarMovimiento(@RequestBody MovimientoInventario m) {
        return ResponseEntity.ok(inventarioService.saveMovimiento(m));
    }
}
