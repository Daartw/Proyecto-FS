package com.mollystore.alertas.service;

import com.mollystore.alertas.dto.JugadorResponseDTO;
import com.mollystore.alertas.entity.*;
import com.mollystore.alertas.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepo;
    private final SuscripcionCartaRepository suscripcionRepo;
    private final WebClient jugadoresWebClient;

    public Alerta enviar(Alerta a) {
        log.info("Enviando alerta tipo={} canal={} a destinatarioId={}", a.getTipo(), a.getCanal(), a.getDestinatarioId());
        a.setFechaCreacion(LocalDateTime.now());
        a.setEstado(EstadoAlerta.PENDIENTE);
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
        return enviar(Alerta.builder()
                .tipo(TipoAlerta.TORNEO)
                .asunto("Nuevo Torneo")
                .mensaje(mensaje)
                .canal(CanalEnvio.EMAIL)
                .build());
    }

    /**
     * Notifica a un jugador que su pedido está listo.
     * Consulta jugadores-service via WebClient para obtener nombre y email real.
     */
    public Alerta notificarPedidoListo(Long jugadorId) {
        log.info("Notificando pedido listo a jugadorId={}", jugadorId);

        String emailDestino = null;
        String nombreJugador = "Cliente";

        try {
            log.info("Consultando datos del jugador id={} en jugadores-service", jugadorId);
            JugadorResponseDTO jugador = jugadoresWebClient.get()
                    .uri("/api/jugadores/{id}", jugadorId)
                    .retrieve()
                    .bodyToMono(JugadorResponseDTO.class)
                    .block();

            if (jugador != null) {
                emailDestino = jugador.getEmail();
                nombreJugador = jugador.getNombre() + " " + jugador.getApellido();
                log.info("Datos del jugador obtenidos: nombre={}, email={}", nombreJugador, emailDestino);
            }

        } catch (WebClientResponseException ex) {
            log.warn("Jugador id={} no encontrado en jugadores-service: status={}", jugadorId, ex.getStatusCode());
        } catch (Exception ex) {
            log.error("Error al consultar jugadores-service para id={}: {}", jugadorId, ex.getMessage());
        }

        Alerta alerta = Alerta.builder()
                .destinatarioId(jugadorId)
                .emailDestino(emailDestino)
                .tipo(TipoAlerta.PEDIDO_LISTO)
                .asunto("Tu pedido está listo, " + nombreJugador)
                .mensaje("Hola " + nombreJugador + ", tu pedido ha sido procesado y está listo para despacho.")
                .canal(CanalEnvio.EMAIL)
                .build();

        return enviar(alerta);
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
