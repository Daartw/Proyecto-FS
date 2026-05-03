package com.mollystore.catalogo.service;

import com.mollystore.catalogo.entity.Carta;
import com.mollystore.catalogo.repository.CartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaService {
    private final CartaRepository cartaRepository;

    public List<Carta> findAll() { return cartaRepository.findAll(); }

    public Carta findById(Long id) {
        return cartaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Carta no encontrada: " + id));
    }

    public List<Carta> buscarPorNombre(String nombre) {
        return cartaRepository.findByNombreEspanolContainingIgnoreCaseOrNombreInglesContainingIgnoreCase(nombre, nombre);
    }

    public List<Carta> findByExpansion(Long expansionId) {
        return cartaRepository.findByExpansionId(expansionId);
    }

    public Carta save(Carta carta) { return cartaRepository.save(carta); }

    public Carta update(Long id, Carta carta) {
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

    public void delete(Long id) { cartaRepository.deleteById(id); }
}
