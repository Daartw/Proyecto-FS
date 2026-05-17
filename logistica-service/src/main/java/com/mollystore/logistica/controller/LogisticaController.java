package com.mollystore.logistica.controller;
import com.mollystore.logistica.entity.*;
import com.mollystore.logistica.service.LogisticaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController @RequestMapping("/api/envios") @RequiredArgsConstructor
public class LogisticaController {
    private final LogisticaService service;

    @GetMapping
    public ResponseEntity<List<Envio>> listar() {
        log.info("GET /api/envios - listando todos los envios");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> detalle(@PathVariable Long id) {
        log.info("GET /api/envios/{} - obteniendo detalle de envio", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<Envio>> porVenta(@PathVariable Long ventaId) {
        log.info("GET /api/envios/venta/{} - obteniendo envios por venta", ventaId);
        return ResponseEntity.ok(service.findByVenta(ventaId));
    }

    @GetMapping("/seguimiento/{codigo}")
    public ResponseEntity<Envio> rastrear(@PathVariable String codigo) {
        log.info("GET /api/envios/seguimiento/{} - rastreando envio por codigo", codigo);
        return ResponseEntity.ok(service.findByCodigo(codigo));
    }

    @PostMapping
    public ResponseEntity<Envio> crear(@Valid @RequestBody Envio envio) {
        log.info("POST /api/envios - creando nuevo envio para ventaId={}", envio.getVentaId());
        return ResponseEntity.ok(service.crear(envio));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Envio> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        log.info("PUT /api/envios/{}/estado - actualizando estado a {}", id, body.get("estado"));
        return ResponseEntity.ok(service.actualizarEstado(id, EstadoEnvio.valueOf(body.get("estado"))));
    }

    @PutMapping("/{id}/despachar")
    public ResponseEntity<Envio> despachar(@PathVariable Long id) {
        log.info("PUT /api/envios/{}/despachar - despachando envio", id);
        return ResponseEntity.ok(service.despachar(id));
    }
}
