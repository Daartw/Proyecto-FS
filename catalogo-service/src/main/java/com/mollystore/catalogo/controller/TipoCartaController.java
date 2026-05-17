package com.mollystore.catalogo.controller;

import com.mollystore.catalogo.entity.TipoCarta;
import com.mollystore.catalogo.repository.TipoCartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos")
@RequiredArgsConstructor
public class TipoCartaController {
    private final TipoCartaRepository tipoCartaRepository;

    @GetMapping
    public ResponseEntity<List<TipoCarta>> listar() {
        return ResponseEntity.ok(tipoCartaRepository.findAll());
    }
}
