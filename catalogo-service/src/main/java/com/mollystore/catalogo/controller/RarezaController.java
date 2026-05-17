package com.mollystore.catalogo.controller;

import com.mollystore.catalogo.entity.Rareza;
import com.mollystore.catalogo.repository.RarezaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rarezas")
@RequiredArgsConstructor
public class RarezaController {
    private final RarezaRepository rarezaRepository;

    @GetMapping
    public ResponseEntity<List<Rareza>> listar() {
        return ResponseEntity.ok(rarezaRepository.findAll());
    }
}
