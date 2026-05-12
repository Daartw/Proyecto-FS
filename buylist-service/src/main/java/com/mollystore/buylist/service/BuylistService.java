package com.mollystore.buylist.service;

import com.mollystore.buylist.entity.*;
import com.mollystore.buylist.repository.SolicitudBuylistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuylistService {
    private final SolicitudBuylistRepository repo;

    public List<SolicitudBuylist> findAll() {
        log.debug("Listando todas las solicitudes buylist");
        return repo.findAll();
    }

    public SolicitudBuylist findById(Long id) {
        log.debug("Buscando solicitud buylist id={}", id);
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud buylist no encontrada con id: " + id));
    }

    public List<SolicitudBuylist> findByCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public SolicitudBuylist crear(SolicitudBuylist s) {
        log.info("Creando solicitud buylist para clienteId={}", s.getClienteId());
        s.setFechaSolicitud(LocalDateTime.now());
        s.setEstado(EstadoBuylist.PENDIENTE);
        return repo.save(s);
    }

    public SolicitudBuylist aprobar(Long id) {
        log.info("Aprobando solicitud buylist id={}", id);
        SolicitudBuylist s = findById(id);
        s.setEstado(EstadoBuylist.APROBADA);
        double credito = s.getDetalles().stream()
            .mapToDouble(d -> d.getValorUnitarioOfrecido() * d.getCantidad()).sum();
        s.setCreditoGenerado(credito);
        log.debug("Credito generado: {}", credito);
        return repo.save(s);
    }

    public SolicitudBuylist rechazar(Long id) {
        log.info("Rechazando solicitud buylist id={}", id);
        SolicitudBuylist s = findById(id);
        s.setEstado(EstadoBuylist.RECHAZADA);
        return repo.save(s);
    }

    public SolicitudBuylist verificar(Long id, SolicitudBuylist updates) {
        log.info("Verificando solicitud buylist id={}", id);
        SolicitudBuylist s = findById(id);
        if (updates.getDetalles() != null) s.setDetalles(updates.getDetalles());
        return repo.save(s);
    }
}
