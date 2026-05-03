package com.mollystore.buylist.service;
import com.mollystore.buylist.entity.*;
import com.mollystore.buylist.repository.SolicitudBuylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class BuylistService {
    private final SolicitudBuylistRepository repo;

    public List<SolicitudBuylist> findAll() { return repo.findAll(); }
    public SolicitudBuylist findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + id));
    }
    public List<SolicitudBuylist> findByCliente(Long clienteId) { return repo.findByClienteId(clienteId); }
    public SolicitudBuylist crear(SolicitudBuylist s) {
        s.setFechaSolicitud(LocalDateTime.now());
        s.setEstado(EstadoBuylist.PENDIENTE);
        return repo.save(s);
    }
    public SolicitudBuylist aprobar(Long id) {
        SolicitudBuylist s = findById(id);
        s.setEstado(EstadoBuylist.APROBADA);
        double credito = s.getDetalles().stream()
            .mapToDouble(d -> d.getValorUnitarioOfrecido() * d.getCantidad()).sum();
        s.setCreditoGenerado(credito);
        return repo.save(s);
    }
    public SolicitudBuylist rechazar(Long id) {
        SolicitudBuylist s = findById(id);
        s.setEstado(EstadoBuylist.RECHAZADA);
        return repo.save(s);
    }
    public SolicitudBuylist verificar(Long id, SolicitudBuylist updates) {
        SolicitudBuylist s = findById(id);
        if (updates.getDetalles() != null) s.setDetalles(updates.getDetalles());
        return repo.save(s);
    }
}
