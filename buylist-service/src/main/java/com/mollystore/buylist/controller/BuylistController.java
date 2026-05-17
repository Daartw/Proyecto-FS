package com.mollystore.buylist.controller;
import com.mollystore.buylist.entity.SolicitudBuylist;
import com.mollystore.buylist.service.BuylistService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController @RequestMapping("/api/buylist") @RequiredArgsConstructor
public class BuylistController {
    private final BuylistService buylistService;

    @GetMapping
    public ResponseEntity<List<SolicitudBuylist>> listar() {
        log.info("GET /api/buylist - listando todas las solicitudes");
        return ResponseEntity.ok(buylistService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudBuylist> detalle(@PathVariable Long id) {
        log.info("GET /api/buylist/{} - obteniendo detalle de solicitud", id);
        return ResponseEntity.ok(buylistService.findById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<SolicitudBuylist>> porCliente(@PathVariable Long clienteId) {
        log.info("GET /api/buylist/cliente/{} - obteniendo solicitudes por cliente", clienteId);
        return ResponseEntity.ok(buylistService.findByCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<SolicitudBuylist> crear(@Valid @RequestBody SolicitudBuylist s) {
        log.info("POST /api/buylist - creando nueva solicitud de buylist para clienteId={}", s.getClienteId());
        return ResponseEntity.ok(buylistService.crear(s));
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<SolicitudBuylist> aprobar(@PathVariable Long id) {
        log.info("PUT /api/buylist/{}/aprobar - aprobando solicitud", id);
        return ResponseEntity.ok(buylistService.aprobar(id));
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<SolicitudBuylist> rechazar(@PathVariable Long id) {
        log.info("PUT /api/buylist/{}/rechazar - rechazando solicitud", id);
        return ResponseEntity.ok(buylistService.rechazar(id));
    }

    @PutMapping("/{id}/verificar")
    public ResponseEntity<SolicitudBuylist> verificar(@PathVariable Long id, @Valid @RequestBody SolicitudBuylist s) {
        log.info("PUT /api/buylist/{}/verificar - verificando solicitud", id);
        return ResponseEntity.ok(buylistService.verificar(id, s));
    }
}
