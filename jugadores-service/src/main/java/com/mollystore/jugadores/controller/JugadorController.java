package com.mollystore.jugadores.controller;

import com.mollystore.jugadores.dto.*;
import com.mollystore.jugadores.entity.*;
import com.mollystore.jugadores.service.JugadorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController @RequestMapping("/api/jugadores") @RequiredArgsConstructor
public class JugadorController {
    private final JugadorService service;

    @GetMapping
    public ResponseEntity<List<JugadorResponseDTO>> listar() {
        log.info("GET /api/jugadores - listando todos los jugadores");
        return ResponseEntity.ok(service.findAll().stream().map(this::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponseDTO> detalle(@PathVariable Long id) {
        log.info("GET /api/jugadores/{} - obteniendo detalle de jugador", id);
        return ResponseEntity.ok(toResponse(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<JugadorResponseDTO> registrar(@Valid @RequestBody JugadorRequestDTO dto) {
        log.info("POST /api/jugadores - registrando nuevo jugador: {} {}", dto.getNombre(), dto.getApellido());
        Jugador j = new Jugador();
        j.setNombre(dto.getNombre());
        j.setApellido(dto.getApellido());
        j.setEmail(dto.getEmail());
        j.setTelefono(dto.getTelefono());
        return ResponseEntity.ok(toResponse(service.registrar(j)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody JugadorRequestDTO dto) {
        log.info("PUT /api/jugadores/{} - actualizando jugador", id);
        Jugador j = new Jugador();
        j.setNombre(dto.getNombre());
        j.setApellido(dto.getApellido());
        j.setEmail(dto.getEmail());
        j.setTelefono(dto.getTelefono());
        return ResponseEntity.ok(toResponse(service.actualizar(id, j)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/jugadores/{} - eliminando jugador", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/puntos")
    public ResponseEntity<Map<String, Integer>> puntos(@PathVariable Long id) {
        log.info("GET /api/jugadores/{}/puntos - consultando puntos", id);
        return ResponseEntity.ok(Map.of("puntos", service.getPuntos(id)));
    }

    @PostMapping("/{id}/puntos/agregar")
    public ResponseEntity<JugadorResponseDTO> agregar(@PathVariable Long id, @Valid @RequestBody AgregarPuntosRequestDTO dto) {
        log.info("POST /api/jugadores/{}/puntos/agregar - agregando {} puntos, motivo={}", id, dto.getPuntos(), dto.getMotivo());
        return ResponseEntity.ok(toResponse(service.agregarPuntos(id, dto.getPuntos(), dto.getMotivo())));
    }

    @PostMapping("/{id}/puntos/canjear")
    public ResponseEntity<JugadorResponseDTO> canjear(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        log.info("POST /api/jugadores/{}/puntos/canjear - canjeando {} puntos", id, body.get("puntos"));
        return ResponseEntity.ok(toResponse(service.canjearPuntos(id, body.get("puntos"))));
    }

    @GetMapping("/{id}/mazos")
    public ResponseEntity<List<MazoPreferido>> mazos(@PathVariable Long id) {
        log.info("GET /api/jugadores/{}/mazos - obteniendo mazos del jugador", id);
        return ResponseEntity.ok(service.getMazos(id));
    }

    @PostMapping("/{id}/mazos")
    public ResponseEntity<MazoPreferido> agregarMazo(@PathVariable Long id, @Valid @RequestBody MazoPreferido mazo) {
        log.info("POST /api/jugadores/{}/mazos - agregando mazo '{}'", id, mazo.getNombreMazo());
        return ResponseEntity.ok(service.agregarMazo(id, mazo));
    }

    private JugadorResponseDTO toResponse(Jugador j) {
        return JugadorResponseDTO.builder()
            .id(j.getId())
            .nombre(j.getNombre())
            .apellido(j.getApellido())
            .email(j.getEmail())
            .telefono(j.getTelefono())
            .fechaRegistro(j.getFechaRegistro())
            .puntosAcumulados(j.getPuntosAcumulados())
            .build();
    }
}
