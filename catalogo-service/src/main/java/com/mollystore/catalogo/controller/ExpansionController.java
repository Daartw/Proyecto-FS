package com.mollystore.catalogo.controller;

import com.mollystore.catalogo.entity.Expansion;
import com.mollystore.catalogo.repository.ExpansionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expansiones")
@RequiredArgsConstructor
public class ExpansionController {
    private final ExpansionRepository expansionRepository;

    @GetMapping
    public List<Expansion> listar() { return expansionRepository.findAll(); }

    @PostMapping
    public Expansion crear(@RequestBody Expansion expansion) {
        return expansionRepository.save(expansion);
    }
}
