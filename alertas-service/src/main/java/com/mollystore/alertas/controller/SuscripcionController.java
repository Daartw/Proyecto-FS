package com.mollystore.alertas.controller;
import com.mollystore.alertas.entity.SuscripcionCarta;
import com.mollystore.alertas.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/suscripciones") @RequiredArgsConstructor
public class SuscripcionController {
    private final AlertaService service;

    @PostMapping public ResponseEntity<SuscripcionCarta> suscribir(@RequestBody SuscripcionCarta s) {
        return ResponseEntity.ok(service.suscribir(s));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelarSuscripcion(id); return ResponseEntity.noContent().build();
    }
    @GetMapping("/jugador/{jugadorId}") public List<SuscripcionCarta> porJugador(@PathVariable Long jugadorId) {
        return service.getSuscripciones(jugadorId);
    }
}
