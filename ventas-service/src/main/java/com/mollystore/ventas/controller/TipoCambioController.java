package com.mollystore.ventas.controller;
import com.mollystore.ventas.entity.TipoCambio;
import com.mollystore.ventas.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/tipo-cambio") @RequiredArgsConstructor
public class TipoCambioController {
    private final VentaService ventaService;

    @GetMapping("/actual")
    public ResponseEntity<TipoCambio> actual() {
        return ResponseEntity.ok(ventaService.getTipoCambioActual());
    }

    @PostMapping
    public ResponseEntity<TipoCambio> actualizar(@Valid @RequestBody TipoCambio tc) {
        return ResponseEntity.ok(ventaService.saveTipoCambio(tc));
    }
}
