package com.mollystore.jugadores_service.controller;

import com.mollystore.jugadores_service.dto.CompraRequestDTO;
import com.mollystore.jugadores_service.model.HistorialCompra;
import com.mollystore.jugadores_service.model.Jugador;
import com.mollystore.jugadores_service.service.JugadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class JugadorController {

    private final JugadorService jugadorService;

    // ── Jugadores ────────────────────────────────────────────────────

    // POST /api/jugadores
    @PostMapping
    public ResponseEntity<Jugador> registrar(@Valid @RequestBody Jugador jugador) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jugadorService.registrarJugador(jugador));
    }

    // GET /api/jugadores
    @GetMapping
    public ResponseEntity<List<Jugador>> listar() {
        return ResponseEntity.ok(jugadorService.listarJugadores());
    }

    // GET /api/jugadores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.obtenerJugador(id));
    }

    // ── Compras e historial ──────────────────────────────────────────

    // POST /api/jugadores/compras
    @PostMapping("/compras")
    public ResponseEntity<HistorialCompra> registrarCompra(@Valid @RequestBody CompraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jugadorService.registrarCompra(dto));
    }

    // GET /api/jugadores/{id}/historial
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<HistorialCompra>> historial(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.obtenerHistorial(id));
    }

    // ── Puntos ───────────────────────────────────────────────────────

    // GET /api/jugadores/{id}/puntos
    @GetMapping("/{id}/puntos")
    public ResponseEntity<Integer> puntos(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.consultarPuntos(id));
    }
}