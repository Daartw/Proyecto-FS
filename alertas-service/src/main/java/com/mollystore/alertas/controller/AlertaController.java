package com.mollystore.alertas.controller;
import com.mollystore.alertas.entity.*;
import com.mollystore.alertas.service.AlertaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController @RequestMapping("/api/alertas") @RequiredArgsConstructor
public class AlertaController {
    private final AlertaService service;

    @PostMapping("/enviar")
    public ResponseEntity<Alerta> enviar(@Valid @RequestBody Alerta a) {
        log.info("POST /api/alertas/enviar - enviando alerta a jugadorId={}", a.getDestinatarioId());
        return ResponseEntity.ok(service.enviar(a));
    }

    @GetMapping
    public ResponseEntity<List<Alerta>> listar() {
        log.info("GET /api/alertas - listando todas las alertas");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> detalle(@PathVariable Long id) {
        log.info("GET /api/alertas/{} - obteniendo detalle de alerta", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<Alerta>> porJugador(@PathVariable Long jugadorId) {
        log.info("GET /api/alertas/jugador/{} - obteniendo alertas por jugador", jugadorId);
        return ResponseEntity.ok(service.findByJugador(jugadorId));
    }

    @PostMapping("/torneo")
    public ResponseEntity<Alerta> torneo(@RequestBody Map<String, String> body) {
        log.info("POST /api/alertas/torneo - enviando notificacion de torneo");
        return ResponseEntity.ok(service.notificarTorneo(body.get("mensaje")));
    }

    @PostMapping("/pedido-listo")
    public ResponseEntity<Alerta> pedidoListo(@RequestBody Map<String, Long> body) {
        log.info("POST /api/alertas/pedido-listo - notificando pedido listo para clienteId={}", body.get("clienteId"));
        return ResponseEntity.ok(service.notificarPedidoListo(body.get("clienteId")));
    }

    @PostMapping("/carta-disponible")
    public ResponseEntity<String> cartaDisponible(@RequestBody Map<String, Long> body) {
        log.info("POST /api/alertas/carta-disponible - notificando carta disponible, cartaId={}", body.get("cartaId"));
        return ResponseEntity.ok("Notificación de carta disponible enviada para cartaId: " + body.get("cartaId"));
    }
}
