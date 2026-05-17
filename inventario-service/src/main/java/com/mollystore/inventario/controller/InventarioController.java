package com.mollystore.inventario.controller;

import com.mollystore.inventario.entity.*;
import com.mollystore.inventario.service.InventarioService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ItemInventario>> listar() {
        log.info("GET /api/inventario - listando todos los items de inventario");
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemInventario> detalle(@PathVariable Long id) {
        log.info("GET /api/inventario/{} - obteniendo detalle de item", id);
        return ResponseEntity.ok(inventarioService.findById(id));
    }

    @GetMapping("/carta/{cartaId}")
    public ResponseEntity<List<ItemInventario>> porCarta(@PathVariable Long cartaId) {
        log.info("GET /api/inventario/carta/{} - obteniendo items por cartaId", cartaId);
        return ResponseEntity.ok(inventarioService.findByCartaId(cartaId));
    }

    @GetMapping("/bajo-stock")
    public ResponseEntity<List<ItemInventario>> bajoStock() {
        log.info("GET /api/inventario/bajo-stock - consultando items con bajo stock");
        return ResponseEntity.ok(inventarioService.findBajoStock());
    }

    @PostMapping
    public ResponseEntity<ItemInventario> agregar(@Valid @RequestBody ItemInventario item) {
        log.info("POST /api/inventario - agregando nuevo item, cartaId={}, cantidad={}", item.getCartaId(), item.getCantidad());
        return ResponseEntity.ok(inventarioService.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemInventario> actualizar(@PathVariable Long id, @Valid @RequestBody ItemInventario item) {
        log.info("PUT /api/inventario/{} - actualizando item de inventario", id);
        return ResponseEntity.ok(inventarioService.update(id, item));
    }

    @PatchMapping("/{id}/descontar")
    public ResponseEntity<Void> descontar(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        log.info("PATCH /api/inventario/{}/descontar - descontando {} unidades", id, body.get("cantidad"));
        inventarioService.descontarStock(id, body.get("cantidad"));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/inventario/{} - eliminando item de inventario", id);
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movimientos")
    public ResponseEntity<List<MovimientoInventario>> movimientos() {
        log.info("GET /api/inventario/movimientos - listando todos los movimientos");
        return ResponseEntity.ok(inventarioService.findAllMovimientos());
    }

    @PostMapping("/movimientos")
    public ResponseEntity<MovimientoInventario> registrarMovimiento(@Valid @RequestBody MovimientoInventario m) {
        log.info("POST /api/inventario/movimientos - registrando movimiento, motivo={}", m.getMotivo());
        return ResponseEntity.ok(inventarioService.saveMovimiento(m));
    }
}
