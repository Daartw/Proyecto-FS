package com.mollystore.alertas.service;

import com.mollystore.alertas.entity.*;
import com.mollystore.alertas.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertaService {
    private final AlertaRepository alertaRepo;
    private final SuscripcionCartaRepository suscripcionRepo;

    public Alerta enviar(Alerta a) {
        log.info("Enviando alerta tipo={} canal={} a destinatarioId={}", a.getTipo(), a.getCanal(), a.getDestinatarioId());
        a.setFechaCreacion(LocalDateTime.now());
        a.setEstado(EstadoAlerta.PENDIENTE);
        // TODO: Integrar con proveedor real de email/WhatsApp
        a.setEstado(EstadoAlerta.ENVIADA);
        a.setFechaEnvio(LocalDateTime.now());
        Alerta saved = alertaRepo.save(a);
        log.debug("Alerta id={} enviada correctamente", saved.getId());
        return saved;
    }

    public List<Alerta> findAll() {
        return alertaRepo.findAll();
    }

    public Alerta findById(Long id) {
        return alertaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Alerta no encontrada con id: " + id));
    }

    public List<Alerta> findByJugador(Long jugadorId) {
        return alertaRepo.findByDestinatarioId(jugadorId);
    }

    public Alerta notificarTorneo(String mensaje) {
        log.info("Notificando torneo: {}", mensaje);
        return enviar(Alerta.builder().tipo(TipoAlerta.TORNEO).asunto("Nuevo Torneo").mensaje(mensaje).canal(CanalEnvio.EMAIL).build());
    }

    public Alerta notificarPedidoListo(Long clienteId) {
        log.info("Notificando pedido listo a clienteId={}", clienteId);
        return enviar(Alerta.builder().destinatarioId(clienteId).tipo(TipoAlerta.PEDIDO_LISTO).asunto("Tu pedido esta listo").canal(CanalEnvio.EMAIL).build());
    }

    public SuscripcionCarta suscribir(SuscripcionCarta s) {
        log.info("Suscribiendo jugadorId={} a cartaId={}", s.getJugadorId(), s.getCartaId());
        s.setFechaSuscripcion(LocalDateTime.now());
        s.setActiva(true);
        return suscripcionRepo.save(s);
    }

    public void cancelarSuscripcion(Long id) {
        log.info("Cancelando suscripcion id={}", id);
        SuscripcionCarta s = suscripcionRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Suscripcion no encontrada con id: " + id));
        s.setActiva(false);
        suscripcionRepo.save(s);
    }

    public List<SuscripcionCarta> getSuscripciones(Long jugadorId) {
        return suscripcionRepo.findByJugadorIdAndActivaTrue(jugadorId);
    }
}
