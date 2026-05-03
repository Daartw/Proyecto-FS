package com.mollystore.alertas.controller;
import com.mollystore.alertas.entity.*;
import com.mollystore.alertas.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/alertas") @RequiredArgsConstructor
public class AlertaController {
    private final AlertaService service;

    @PostMapping("/enviar") public ResponseEntity<Alerta> enviar(@RequestBody Alerta a) {
        return ResponseEntity.ok(service.enviar(a));
    }
    @GetMapping public List<Alerta> listar() { return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Alerta> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/jugador/{jugadorId}") public List<Alerta> porJugador(@PathVariable Long jugadorId) {
        return service.findByJugador(jugadorId);
    }
    @PostMapping("/torneo") public ResponseEntity<Alerta> torneo(@RequestBody Map<String,String> body) {
        return ResponseEntity.ok(service.notificarTorneo(body.get("mensaje")));
    }
    @PostMapping("/pedido-listo") public ResponseEntity<Alerta> pedidoListo(@RequestBody Map<String,Long> body) {
        return ResponseEntity.ok(service.notificarPedidoListo(body.get("clienteId")));
    }
    @PostMapping("/carta-disponible") public ResponseEntity<String> cartaDisponible(@RequestBody Map<String,Long> body) {
        return ResponseEntity.ok("Notificación de carta disponible enviada para cartaId: " + body.get("cartaId"));
    }
}
