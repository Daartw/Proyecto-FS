package com.mollystore.buylist.controller;
import com.mollystore.buylist.entity.SolicitudBuylist;
import com.mollystore.buylist.service.BuylistService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController @RequestMapping("/api/buylist") @RequiredArgsConstructor
public class BuylistController {
    private final BuylistService buylistService;

    @GetMapping public List<SolicitudBuylist> listar() { return buylistService.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<SolicitudBuylist> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(buylistService.findById(id));
    }
    @GetMapping("/cliente/{clienteId}") public List<SolicitudBuylist> porCliente(@PathVariable Long clienteId) {
        return buylistService.findByCliente(clienteId);
    }
    @PostMapping public ResponseEntity<SolicitudBuylist> crear(@RequestBody SolicitudBuylist s) {
        return ResponseEntity.ok(buylistService.crear(s));
    }
    @PutMapping("/{id}/aprobar") public ResponseEntity<SolicitudBuylist> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(buylistService.aprobar(id));
    }
    @PutMapping("/{id}/rechazar") public ResponseEntity<SolicitudBuylist> rechazar(@PathVariable Long id) {
        return ResponseEntity.ok(buylistService.rechazar(id));
    }
    @PutMapping("/{id}/verificar") public ResponseEntity<SolicitudBuylist> verificar(
            @PathVariable Long id, @RequestBody SolicitudBuylist s) {
        return ResponseEntity.ok(buylistService.verificar(id, s));
    }
}
