package com.mollystore.sincronizacion.service;

import com.mollystore.sincronizacion.dto.InventarioResponseDTO;
import com.mollystore.sincronizacion.entity.*;
import com.mollystore.sincronizacion.repository.EventoSincronizacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SincronizacionService {

    private final EventoSincronizacionRepository repo;
    private final WebClient inventarioWebClient;

    /**
     * Descuenta stock en inventario-service via WebClient,
     * luego registra el evento de sincronización.
     */
    public EventoSincronizacion descontar(EventoSincronizacion evento) {
        log.info("Iniciando sincronizacion: ventaId={}, itemId={}, cantidad={}",
                evento.getVentaId(), evento.getItemInventarioId(), evento.getCantidadDescontada());

        evento.setFechaEvento(LocalDateTime.now());
        evento.setOrigen("ventas-service");

        try {
            // 1. Verificar que el item existe en inventario-service
            log.info("Consultando item id={} en inventario-service", evento.getItemInventarioId());
            InventarioResponseDTO item = inventarioWebClient.get()
                    .uri("/api/inventario/{id}", evento.getItemInventarioId())
                    .retrieve()
                    .bodyToMono(InventarioResponseDTO.class)
                    .block();

            log.info("Item encontrado en inventario: cartaId={}, stockActual={}",
                    item.getCartaId(), item.getCantidad());

            // 2. Validar stock antes de descontar
            if (item.getCantidad() < evento.getCantidadDescontada()) {
                log.warn("Stock insuficiente en inventario-service: disponible={}, solicitado={}",
                        item.getCantidad(), evento.getCantidadDescontada());
                evento.setEstado(EstadoSincronizacion.FALLIDO);
                evento.setMensajeError("Stock insuficiente en inventario. Disponible: "
                        + item.getCantidad() + ", Solicitado: " + evento.getCantidadDescontada());
                return repo.save(evento);
            }

            // 3. Ejecutar el descuento en inventario-service
            log.info("Ejecutando descuento de {} unidades en inventario-service para item id={}",
                    evento.getCantidadDescontada(), evento.getItemInventarioId());

            inventarioWebClient.patch()
                    .uri("/api/inventario/{id}/descontar", evento.getItemInventarioId())
                    .bodyValue(Map.of("cantidad", evento.getCantidadDescontada()))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            log.info("Descuento ejecutado correctamente en inventario-service");
            evento.setEstado(EstadoSincronizacion.EXITOSO);

        } catch (WebClientResponseException ex) {
            log.error("Error al comunicarse con inventario-service: status={}, body={}",
                    ex.getStatusCode(), ex.getResponseBodyAsString());
            evento.setEstado(EstadoSincronizacion.FALLIDO);
            evento.setMensajeError("Error en inventario-service: " + ex.getMessage());

        } catch (Exception ex) {
            log.error("Error inesperado al sincronizar con inventario-service: {}", ex.getMessage());
            evento.setEstado(EstadoSincronizacion.FALLIDO);
            evento.setMensajeError("Error de comunicacion: " + ex.getMessage());
        }

        return repo.save(evento);
    }

    public List<EventoSincronizacion> findAll() {
        return repo.findAll();
    }

    public List<EventoSincronizacion> findByVenta(Long ventaId) {
        return repo.findByVentaId(ventaId);
    }

    public EventoSincronizacion revertir(Long id) {
        log.warn("Revirtiendo evento de sincronizacion id={}", id);
        EventoSincronizacion e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento de sincronizacion no encontrado con id: " + id));
        e.setEstado(EstadoSincronizacion.FALLIDO);
        e.setMensajeError("Revertido manualmente");
        return repo.save(e);
    }
}
