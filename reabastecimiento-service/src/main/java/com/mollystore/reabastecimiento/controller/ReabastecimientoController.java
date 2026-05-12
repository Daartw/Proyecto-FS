package com.mollystore.reabastecimiento.controller;
import com.mollystore.reabastecimiento.entity.*;
import com.mollystore.reabastecimiento.service.ReabastecimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/reabastecimiento") @RequiredArgsConstructor
public class ReabastecimientoController {
    private final ReabastecimientoService service;

    @GetMapping("/ordenes") public List<OrdenReabastecimiento> listar() { return service.findAll(); }
    @GetMapping("/ordenes/{id}") public ResponseEntity<OrdenReabastecimiento> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping("/ordenes") public ResponseEntity<OrdenReabastecimiento> crear(@RequestBody OrdenReabastecimiento o) {
        return ResponseEntity.ok(service.crear(o));
    }
    @PutMapping("/ordenes/{id}/recibir") public ResponseEntity<OrdenReabastecimiento> recibir(@PathVariable Long id) {
        return ResponseEntity.ok(service.recibir(id));
    }
    @PutMapping("/ordenes/{id}/cancelar") public ResponseEntity<OrdenReabastecimiento> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
    @PostMapping("/auto-orden") public ResponseEntity<String> autoOrden() {
        return ResponseEntity.ok("Auto-orden verificada");
    }
    @GetMapping("/distribuidores") public List<Distribuidor> distribuidores() {
        return service.findAllDistribuidores();
    }
    @PostMapping("/distribuidores") public ResponseEntity<Distribuidor> agregarDistribuidor(@RequestBody Distribuidor d) {
        return ResponseEntity.ok(service.saveDistribuidor(d));
    }
}
