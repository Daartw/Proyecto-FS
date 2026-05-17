package com.mollystore.reabastecimiento.controller;
import com.mollystore.reabastecimiento.entity.*;
import com.mollystore.reabastecimiento.service.ReabastecimientoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController @RequestMapping("/api/reabastecimiento") @RequiredArgsConstructor
public class ReabastecimientoController {
    private final ReabastecimientoService service;

    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenReabastecimiento>> listar() {
        log.info("GET /api/reabastecimiento/ordenes - listando todas las ordenes");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/ordenes/{id}")
    public ResponseEntity<OrdenReabastecimiento> detalle(@PathVariable Long id) {
        log.info("GET /api/reabastecimiento/ordenes/{} - obteniendo detalle de orden", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/ordenes")
    public ResponseEntity<OrdenReabastecimiento> crear(@Valid @RequestBody OrdenReabastecimiento o) {
        log.info("POST /api/reabastecimiento/ordenes - creando nueva orden de reabastecimiento");
        return ResponseEntity.ok(service.crear(o));
    }

    @PutMapping("/ordenes/{id}/recibir")
    public ResponseEntity<OrdenReabastecimiento> recibir(@PathVariable Long id) {
        log.info("PUT /api/reabastecimiento/ordenes/{}/recibir - recibiendo orden", id);
        return ResponseEntity.ok(service.recibir(id));
    }

    @PutMapping("/ordenes/{id}/cancelar")
    public ResponseEntity<OrdenReabastecimiento> cancelar(@PathVariable Long id) {
        log.info("PUT /api/reabastecimiento/ordenes/{}/cancelar - cancelando orden", id);
        return ResponseEntity.ok(service.cancelar(id));
    }

    @PostMapping("/auto-orden")
    public ResponseEntity<String> autoOrden() {
        log.info("POST /api/reabastecimiento/auto-orden - verificando auto-orden");
        return ResponseEntity.ok("Auto-orden verificada");
    }

    @GetMapping("/distribuidores")
    public ResponseEntity<List<Distribuidor>> distribuidores() {
        log.info("GET /api/reabastecimiento/distribuidores - listando distribuidores");
        return ResponseEntity.ok(service.findAllDistribuidores());
    }

    @PostMapping("/distribuidores")
    public ResponseEntity<Distribuidor> agregarDistribuidor(@Valid @RequestBody Distribuidor d) {
        log.info("POST /api/reabastecimiento/distribuidores - agregando distribuidor: {}", d.getNombre());
        return ResponseEntity.ok(service.saveDistribuidor(d));
    }
}
