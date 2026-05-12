package com.mollystore.sincronizacion.service;

import com.mollystore.sincronizacion.entity.*;
import com.mollystore.sincronizacion.repository.EventoSincronizacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SincronizacionService {
    private final EventoSincronizacionRepository repo;

    public EventoSincronizacion descontar(EventoSincronizacion evento) {
        log.info("Sincronizando descuento: ventaId={}, itemId={}, cantidad={}", evento.getVentaId(), evento.getItemInventarioId(), evento.getCantidadDescontada());
        evento.setFechaEvento(LocalDateTime.now());
        evento.setEstado(EstadoSincronizacion.EXITOSO);
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
