package com.mollystore.alertas.service;
import com.mollystore.alertas.entity.*;
import com.mollystore.alertas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class AlertaService {
    private final AlertaRepository alertaRepo;
    private final SuscripcionCartaRepository suscripcionRepo;

    public Alerta enviar(Alerta a) {
        a.setFechaCreacion(LocalDateTime.now());
        a.setEstado(EstadoAlerta.PENDIENTE);
        // Aquí iría integración real con email/WhatsApp
        a.setEstado(EstadoAlerta.ENVIADA);
        a.setFechaEnvio(LocalDateTime.now());
        return alertaRepo.save(a);
    }
    public List<Alerta> findAll() { return alertaRepo.findAll(); }
    public Alerta findById(Long id) { return alertaRepo.findById(id).orElseThrow(); }
    public List<Alerta> findByJugador(Long jugadorId) { return alertaRepo.findByDestinatarioId(jugadorId); }
    public Alerta notificarTorneo(String mensaje) {
        return enviar(Alerta.builder().tipo(TipoAlerta.TORNEO).asunto("Nuevo Torneo").mensaje(mensaje).canal(CanalEnvio.EMAIL).build());
    }
    public Alerta notificarPedidoListo(Long clienteId) {
        return enviar(Alerta.builder().destinatarioId(clienteId).tipo(TipoAlerta.PEDIDO_LISTO).asunto("Tu pedido está listo").canal(CanalEnvio.EMAIL).build());
    }
    public SuscripcionCarta suscribir(SuscripcionCarta s) {
        s.setFechaSuscripcion(LocalDateTime.now()); s.setActiva(true); return suscripcionRepo.save(s);
    }
    public void cancelarSuscripcion(Long id) {
        SuscripcionCarta s = suscripcionRepo.findById(id).orElseThrow();
        s.setActiva(false); suscripcionRepo.save(s);
    }
    public List<SuscripcionCarta> getSuscripciones(Long jugadorId) {
        return suscripcionRepo.findByJugadorIdAndActivaTrue(jugadorId);
    }
}
