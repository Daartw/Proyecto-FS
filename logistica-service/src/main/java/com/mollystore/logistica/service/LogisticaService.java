package com.mollystore.logistica.service;

import com.mollystore.logistica.entity.*;
import com.mollystore.logistica.repository.EnvioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogisticaService {
    private final EnvioRepository repo;

    public List<Envio> findAll() {
        log.debug("Listando todos los envios");
        return repo.findAll();
    }

    public Envio findById(Long id) {
        log.debug("Buscando envio id={}", id);
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Envio no encontrado con id: " + id));
    }

    public List<Envio> findByVenta(Long ventaId) {
        return repo.findByVentaId(ventaId);
    }

    public Envio findByCodigo(String codigo) {
        log.debug("Rastreando envio con codigo={}", codigo);
        return repo.findByCodigoSeguimiento(codigo)
            .orElseThrow(() -> new RuntimeException("Codigo de seguimiento no encontrado: " + codigo));
    }

    public Envio crear(Envio envio) {
        log.info("Creando envio para ventaId={}, destino={}", envio.getVentaId(), envio.getCiudad());
        envio.setEstado(EstadoEnvio.PREPARANDO);
        envio.setCodigoSeguimiento("TRK-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        return repo.save(envio);
    }

    public Envio actualizarEstado(Long id, EstadoEnvio estado) {
        log.info("Actualizando estado de envio id={} a {}", id, estado);
        Envio e = findById(id);
        e.setEstado(estado);
        return repo.save(e);
    }

    public Envio despachar(Long id) {
        log.info("Despachando envio id={}", id);
        Envio e = findById(id);
        e.setEstado(EstadoEnvio.DESPACHADO);
        e.setFechaDespacho(LocalDateTime.now());
        return repo.save(e);
    }
}
