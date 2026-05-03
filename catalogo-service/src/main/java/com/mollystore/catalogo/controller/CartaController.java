package com.mollystore.catalogo.controller;

import com.mollystore.catalogo.entity.Carta;
import com.mollystore.catalogo.service.CartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cartas")
@RequiredArgsConstructor
public class CartaController {
    private final CartaService cartaService;

    @GetMapping
    public List<Carta> listarTodas() { return cartaService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Carta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cartaService.findById(id));
    }

    @GetMapping("/buscar")
    public List<Carta> buscarPorNombre(@RequestParam String nombre) {
        return cartaService.buscarPorNombre(nombre);
    }

    @GetMapping("/expansion/{id}")
    public List<Carta> porExpansion(@PathVariable Long id) {
        return cartaService.findByExpansion(id);
    }

    @PostMapping
    public ResponseEntity<Carta> crear(@Valid @RequestBody Carta carta) {
        return ResponseEntity.ok(cartaService.save(carta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carta> actualizar(@PathVariable Long id, @Valid @RequestBody Carta carta) {
        return ResponseEntity.ok(cartaService.update(id, carta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cartaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
