package com.mollystore.jugadores.controller;
import com.mollystore.jugadores.entity.*;
import com.mollystore.jugadores.service.JugadorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController @RequestMapping("/api/jugadores") @RequiredArgsConstructor
public class JugadorController {
    private final JugadorService service;

    @GetMapping public List<Jugador> listar() { return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Jugador> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping public ResponseEntity<Jugador> registrar(@Valid @RequestBody Jugador j) {
        return ResponseEntity.ok(service.registrar(j));
    }
    @PutMapping("/{id}") public ResponseEntity<Jugador> actualizar(@PathVariable Long id, @Valid @RequestBody Jugador j) {
        return ResponseEntity.ok(service.actualizar(id, j));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id); return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/puntos") public ResponseEntity<Map<String,Integer>> puntos(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("puntos", service.getPuntos(id)));
    }
    @PostMapping("/{id}/puntos/agregar") public ResponseEntity<Jugador> agregar(
            @PathVariable Long id, @Valid @RequestBody Map<String,Object> body) {
        return ResponseEntity.ok(service.agregarPuntos(id, (Integer)body.get("puntos"), (String)body.get("motivo")));
    }
    @PostMapping("/{id}/puntos/canjear") public ResponseEntity<Jugador> canjear(
            @PathVariable Long id, @Valid @RequestBody Map<String,Integer> body) {
        return ResponseEntity.ok(service.canjearPuntos(id, body.get("puntos")));
    }
    @GetMapping("/{id}/mazos") public List<MazoPreferido> mazos(@PathVariable Long id) {
        return service.getMazos(id);
    }
    @PostMapping("/{id}/mazos") public ResponseEntity<MazoPreferido> agregarMazo(
            @PathVariable Long id, @Valid @RequestBody MazoPreferido mazo) {
        return ResponseEntity.ok(service.agregarMazo(id, mazo));
    }
}
