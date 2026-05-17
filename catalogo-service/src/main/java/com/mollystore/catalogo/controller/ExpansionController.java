package com.mollystore.catalogo.controller;

import com.mollystore.catalogo.entity.Expansion;
import com.mollystore.catalogo.repository.ExpansionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expansiones")
@RequiredArgsConstructor
public class ExpansionController {
    private final ExpansionRepository expansionRepository;

    @GetMapping
    public ResponseEntity<List<Expansion>> listar() {
        return ResponseEntity.ok(expansionRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Expansion> crear(@Valid @RequestBody Expansion expansion) {
        return ResponseEntity.ok(expansionRepository.save(expansion));
    }
}
