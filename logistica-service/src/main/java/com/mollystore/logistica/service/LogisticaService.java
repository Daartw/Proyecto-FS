package com.mollystore.logistica.service;
import com.mollystore.logistica.entity.*;
import com.mollystore.logistica.repository.EnvioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class LogisticaService {
    private final EnvioRepository repo;

    public List<Envio> findAll() { return repo.findAll(); }
    public Envio findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Envio no encontrado: " + id));
    }
    public List<Envio> findByVenta(Long ventaId) { return repo.findByVentaId(ventaId); }
    public Envio findByCodigo(String codigo) {
        return repo.findByCodigoSeguimiento(codigo).orElseThrow(() -> new RuntimeException("Código no encontrado"));
    }
    public Envio crear(Envio envio) {
        envio.setEstado(EstadoEnvio.PREPARANDO);
        envio.setCodigoSeguimiento("TRK-" + UUID.randomUUID().toString().substring(0,10).toUpperCase());
        return repo.save(envio);
    }
    public Envio actualizarEstado(Long id, EstadoEnvio estado) {
        Envio e = findById(id); e.setEstado(estado); return repo.save(e);
    }
    public Envio despachar(Long id) {
        Envio e = findById(id);
        e.setEstado(EstadoEnvio.DESPACHADO);
        e.setFechaDespacho(LocalDateTime.now());
        return repo.save(e);
    }
}
