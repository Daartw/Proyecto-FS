package com.mollystore.logistica.controller;
import com.mollystore.logistica.entity.*;
import com.mollystore.logistica.service.LogisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/envios") @RequiredArgsConstructor
public class LogisticaController {
    private final LogisticaService service;

    @GetMapping public List<Envio> listar() { return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Envio> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/venta/{ventaId}") public List<Envio> porVenta(@PathVariable Long ventaId) {
        return service.findByVenta(ventaId);
    }
    @GetMapping("/seguimiento/{codigo}") public ResponseEntity<Envio> rastrear(@PathVariable String codigo) {
        return ResponseEntity.ok(service.findByCodigo(codigo));
    }
    @PostMapping public ResponseEntity<Envio> crear(@RequestBody Envio envio) {
        return ResponseEntity.ok(service.crear(envio));
    }
    @PutMapping("/{id}/estado") public ResponseEntity<Envio> actualizarEstado(
            @PathVariable Long id, @RequestBody Map<String,String> body) {
        return ResponseEntity.ok(service.actualizarEstado(id, EstadoEnvio.valueOf(body.get("estado"))));
    }
    @PutMapping("/{id}/despachar") public ResponseEntity<Envio> despachar(@PathVariable Long id) {
        return ResponseEntity.ok(service.despachar(id));
    }
}
