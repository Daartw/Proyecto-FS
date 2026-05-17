package com.mollystore.sincronizacion.controller;
import com.mollystore.sincronizacion.entity.EventoSincronizacion;
import com.mollystore.sincronizacion.service.SincronizacionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController @RequestMapping("/api/sincronizacion") @RequiredArgsConstructor
public class SincronizacionController {
    private final SincronizacionService service;

    @PostMapping("/descontar")
    public ResponseEntity<EventoSincronizacion> descontar(@Valid @RequestBody EventoSincronizacion e) {
        log.info("POST /api/sincronizacion/descontar - ventaId={}", e.getVentaId());
        return ResponseEntity.ok(service.descontar(e));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<EventoSincronizacion>> listar() {
        log.info("GET /api/sincronizacion/eventos - listando todos los eventos");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/eventos/{ventaId}")
    public ResponseEntity<List<EventoSincronizacion>> porVenta(@PathVariable Long ventaId) {
        log.info("GET /api/sincronizacion/eventos/{} - eventos por venta", ventaId);
        return ResponseEntity.ok(service.findByVenta(ventaId));
    }

    @PostMapping("/revertir/{id}")
    public ResponseEntity<EventoSincronizacion> revertir(@PathVariable Long id) {
        log.info("POST /api/sincronizacion/revertir/{} - revirtiendo evento", id);
        return ResponseEntity.ok(service.revertir(id));
    }
}
