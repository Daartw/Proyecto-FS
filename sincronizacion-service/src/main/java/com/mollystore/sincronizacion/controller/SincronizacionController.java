package com.mollystore.sincronizacion.controller;
import com.mollystore.sincronizacion.entity.EventoSincronizacion;
import com.mollystore.sincronizacion.service.SincronizacionService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController @RequestMapping("/api/sincronizacion") @RequiredArgsConstructor
public class SincronizacionController {
    private final SincronizacionService service;

    @PostMapping("/descontar") public ResponseEntity<EventoSincronizacion> descontar(@RequestBody EventoSincronizacion e) {
        return ResponseEntity.ok(service.descontar(e));
    }
    @GetMapping("/eventos") public List<EventoSincronizacion> listar() { return service.findAll(); }
    @GetMapping("/eventos/{ventaId}") public List<EventoSincronizacion> porVenta(@PathVariable Long ventaId) {
        return service.findByVenta(ventaId);
    }
    @PostMapping("/revertir/{id}") public ResponseEntity<EventoSincronizacion> revertir(@PathVariable Long id) {
        return ResponseEntity.ok(service.revertir(id));
    }
}
