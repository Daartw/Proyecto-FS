package com.mollystore.jugadores.service;
import com.mollystore.jugadores.entity.*;
import com.mollystore.jugadores.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class JugadorService {
    private final JugadorRepository jugadorRepo;
    private final MazoPreferidoRepository mazoRepo;
    private final MovimientoPuntosRepository puntosRepo;

    public List<Jugador> findAll() { return jugadorRepo.findAll(); }
    public Jugador findById(Long id) {
        return jugadorRepo.findById(id).orElseThrow(() -> new RuntimeException("Jugador no encontrado: " + id));
    }
    public Jugador registrar(Jugador j) {
        j.setFechaRegistro(LocalDate.now());
        j.setPuntosAcumulados(0);
        return jugadorRepo.save(j);
    }
    public Jugador actualizar(Long id, Jugador j) {
        Jugador e = findById(id);
        e.setNombre(j.getNombre()); e.setApellido(j.getApellido());
        e.setEmail(j.getEmail()); e.setTelefono(j.getTelefono());
        return jugadorRepo.save(e);
    }
    public void eliminar(Long id) { jugadorRepo.deleteById(id); }
    public Integer getPuntos(Long id) { return findById(id).getPuntosAcumulados(); }
    public Jugador agregarPuntos(Long id, Integer puntos, String motivo) {
        Jugador j = findById(id);
        j.setPuntosAcumulados(j.getPuntosAcumulados() + puntos);
        jugadorRepo.save(j);
        puntosRepo.save(MovimientoPuntos.builder().jugador(j).puntos(puntos).motivo(motivo).fecha(LocalDateTime.now()).build());
        return j;
    }
    public Jugador canjearPuntos(Long id, Integer puntos) {
        Jugador j = findById(id);
        if (j.getPuntosAcumulados() < puntos) throw new RuntimeException("Puntos insuficientes");
        j.setPuntosAcumulados(j.getPuntosAcumulados() - puntos);
        jugadorRepo.save(j);
        puntosRepo.save(MovimientoPuntos.builder().jugador(j).puntos(-puntos).motivo("CANJE").fecha(LocalDateTime.now()).build());
        return j;
    }
    public List<MazoPreferido> getMazos(Long jugadorId) { return mazoRepo.findByJugadorId(jugadorId); }
    public MazoPreferido agregarMazo(Long jugadorId, MazoPreferido mazo) {
        mazo.setJugador(findById(jugadorId));
        return mazoRepo.save(mazo);
    }
}
