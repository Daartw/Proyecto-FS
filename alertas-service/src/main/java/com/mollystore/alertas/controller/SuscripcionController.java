package com.mollystore.alertas.controller;
import com.mollystore.alertas.entity.SuscripcionCarta;
import com.mollystore.alertas.service.AlertaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController @RequestMapping("/api/suscripciones") @RequiredArgsConstructor
public class SuscripcionController {
    private final AlertaService service;

    @PostMapping
    public ResponseEntity<SuscripcionCarta> suscribir(@Valid @RequestBody SuscripcionCarta s) {
        log.info("POST /api/suscripciones - suscribiendo jugadorId={} a cartaId={}", s.getJugadorId(), s.getCartaId());
        return ResponseEntity.ok(service.suscribir(s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        log.info("DELETE /api/suscripciones/{} - cancelando suscripcion", id);
        service.cancelarSuscripcion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<SuscripcionCarta>> porJugador(@PathVariable Long jugadorId) {
        log.info("GET /api/suscripciones/jugador/{} - obteniendo suscripciones por jugador", jugadorId);
        return ResponseEntity.ok(service.getSuscripciones(jugadorId));
    }
}
