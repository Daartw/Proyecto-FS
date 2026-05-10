package com.mollystore.jugadores_service.service;

import com.mollystore.jugadores_service.dto.CompraRequestDTO;
import com.mollystore.jugadores_service.model.HistorialCompra;
import com.mollystore.jugadores_service.model.Jugador;
import com.mollystore.jugadores_service.repository.HistorialCompraRepository;
import com.mollystore.jugadores_service.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JugadorService {

    private final JugadorRepository jugadorRepo;
    private final HistorialCompraRepository compraRepo;

    private int calcularPuntos(HistorialCompra.TipoCompra tipo, java.math.BigDecimal monto) {
        return switch (tipo) {
            case SOBRE            -> monto.intValue() * 10;
            case TORNEO           -> 50;
            case MAZO             -> monto.intValue() * 5;
            case CARTA_INDIVIDUAL -> monto.intValue() * 2;
        };
    }

    @Transactional
    public Jugador registrarJugador(Jugador jugador) {
        if (jugadorRepo.existsByEmail(jugador.getEmail())) {
            throw new IllegalArgumentException("Ya existe un jugador con el email: " + jugador.getEmail());
        }
        return jugadorRepo.save(jugador);
    }

    public Jugador obtenerJugador(Long id) {
        return jugadorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
    }

    public List<Jugador> listarJugadores() {
        return jugadorRepo.findAll();
    }

    @Transactional
    public HistorialCompra registrarCompra(CompraRequestDTO dto) {
        Jugador jugador = obtenerJugador(dto.getJugadorId());
        int puntos = calcularPuntos(dto.getTipoCompra(), dto.getMonto());

        HistorialCompra compra = HistorialCompra.builder()
                .jugador(jugador)
                .productoId(dto.getProductoId())
                .nombreProducto(dto.getNombreProducto())
                .tipoCompra(dto.getTipoCompra())
                .monto(dto.getMonto())
                .puntosGenerados(puntos)
                .build();

        jugador.setPuntosAcumulados(jugador.getPuntosAcumulados() + puntos);
        jugadorRepo.save(jugador);

        return compraRepo.save(compra);
    }

    public List<HistorialCompra> obtenerHistorial(Long jugadorId) {
        obtenerJugador(jugadorId);
        return compraRepo.findByJugadorIdOrderByFechaCompraDesc(jugadorId);
    }

    public Integer consultarPuntos(Long jugadorId) {
        return obtenerJugador(jugadorId).getPuntosAcumulados();
    }
}