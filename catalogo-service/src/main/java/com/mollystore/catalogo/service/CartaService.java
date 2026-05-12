package com.mollystore.catalogo.service;

import com.mollystore.catalogo.entity.Carta;
import com.mollystore.catalogo.repository.CartaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaService {
    private final CartaRepository cartaRepository;

    public List<Carta> findAll() {
        log.debug("Listando todas las cartas");
        return cartaRepository.findAll();
    }

    public Carta findById(Long id) {
        log.debug("Buscando carta con id={}", id);
        return cartaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Carta no encontrada con id: " + id));
    }

    public List<Carta> buscarPorNombre(String nombre) {
        log.debug("Buscando cartas por nombre: {}", nombre);
        return cartaRepository.findByNombreEspanolContainingIgnoreCaseOrNombreInglesContainingIgnoreCase(nombre, nombre);
    }

    public List<Carta> findByExpansion(Long expansionId) {
        log.debug("Buscando cartas por expansion id={}", expansionId);
        return cartaRepository.findByExpansionId(expansionId);
    }

    public Carta save(Carta carta) {
        log.info("Guardando nueva carta: {}", carta.getNombreEspanol());
        return cartaRepository.save(carta);
    }

    public Carta update(Long id, Carta carta) {
        log.info("Actualizando carta id={}", id);
        Carta existing = findById(id);
        existing.setNombreIngles(carta.getNombreIngles());
        existing.setNombreEspanol(carta.getNombreEspanol());
        existing.setDescripcionIngles(carta.getDescripcionIngles());
        existing.setDescripcionEspanol(carta.getDescripcionEspanol());
        existing.setImagen(carta.getImagen());
        existing.setExpansion(carta.getExpansion());
        existing.setRareza(carta.getRareza());
        existing.setTipo(carta.getTipo());
        return cartaRepository.save(existing);
    }

    public void delete(Long id) {
        log.info("Eliminando carta id={}", id);
        cartaRepository.deleteById(id);
    }
}
