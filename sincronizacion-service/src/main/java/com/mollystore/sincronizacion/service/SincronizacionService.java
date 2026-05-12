package com.mollystore.sincronizacion.service;
import com.mollystore.sincronizacion.entity.*;
import com.mollystore.sincronizacion.repository.EventoSincronizacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class SincronizacionService {
    private final EventoSincronizacionRepository repo;

    public EventoSincronizacion descontar(EventoSincronizacion evento) {
        evento.setFechaEvento(LocalDateTime.now());
        evento.setEstado(EstadoSincronizacion.EXITOSO);
        return repo.save(evento);
    }
    public List<EventoSincronizacion> findAll() { return repo.findAll(); }
    public List<EventoSincronizacion> findByVenta(Long ventaId) { return repo.findByVentaId(ventaId); }
    public EventoSincronizacion revertir(Long id) {
        EventoSincronizacion e = repo.findById(id).orElseThrow();
        e.setEstado(EstadoSincronizacion.FALLIDO);
        e.setMensajeError("Revertido manualmente");
        return repo.save(e);
    }
}
