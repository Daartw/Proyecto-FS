package com.mollystore.jugadores.service;

import com.mollystore.jugadores.entity.*;
import com.mollystore.jugadores.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JugadorService {
    private final JugadorRepository jugadorRepo;
    private final MazoPreferidoRepository mazoRepo;
    private final MovimientoPuntosRepository puntosRepo;

    public List<Jugador> findAll() {
        log.debug("Listando todos los jugadores");
        return jugadorRepo.findAll();
    }

    public Jugador findById(Long id) {
        log.debug("Buscando jugador id={}", id);
        return jugadorRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
    }

    public Jugador registrar(Jugador j) {
        log.info("Registrando nuevo jugador: {} {}", j.getNombre(), j.getApellido());
        j.setFechaRegistro(LocalDate.now());
        j.setPuntosAcumulados(0);
        return jugadorRepo.save(j);
    }

    public Jugador actualizar(Long id, Jugador j) {
        log.info("Actualizando jugador id={}", id);
        Jugador e = findById(id);
        e.setNombre(j.getNombre());
        e.setApellido(j.getApellido());
        e.setEmail(j.getEmail());
        e.setTelefono(j.getTelefono());
        return jugadorRepo.save(e);
    }

    public void eliminar(Long id) {
        log.info("Eliminando jugador id={}", id);
        jugadorRepo.deleteById(id);
    }

    public Integer getPuntos(Long id) {
        return findById(id).getPuntosAcumulados();
    }

    public Jugador agregarPuntos(Long id, Integer puntos, String motivo) {
        log.info("Agregando {} puntos al jugador id={}, motivo={}", puntos, id, motivo);
        Jugador j = findById(id);
        j.setPuntosAcumulados(j.getPuntosAcumulados() + puntos);
        jugadorRepo.save(j);
        puntosRepo.save(MovimientoPuntos.builder().jugador(j).puntos(puntos).motivo(motivo).fecha(LocalDateTime.now()).build());
        return j;
    }

    public Jugador canjearPuntos(Long id, Integer puntos) {
        log.info("Canjeando {} puntos del jugador id={}", puntos, id);
        Jugador j = findById(id);
        if (j.getPuntosAcumulados() < puntos) {
            log.warn("Puntos insuficientes para jugador id={}: tiene={}, solicita={}", id, j.getPuntosAcumulados(), puntos);
            throw new RuntimeException("Puntos insuficientes. Disponible: " + j.getPuntosAcumulados() + ", Solicitado: " + puntos);
        }
        j.setPuntosAcumulados(j.getPuntosAcumulados() - puntos);
        jugadorRepo.save(j);
        puntosRepo.save(MovimientoPuntos.builder().jugador(j).puntos(-puntos).motivo("CANJE").fecha(LocalDateTime.now()).build());
        return j;
    }

    public List<MazoPreferido> getMazos(Long jugadorId) {
        return mazoRepo.findByJugadorId(jugadorId);
    }

    public MazoPreferido agregarMazo(Long jugadorId, MazoPreferido mazo) {
        log.info("Agregando mazo '{}' al jugador id={}", mazo.getNombreMazo(), jugadorId);
        mazo.setJugador(findById(jugadorId));
        return mazoRepo.save(mazo);
    }
}
