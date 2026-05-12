package com.mollystore.reabastecimiento.service;

import com.mollystore.reabastecimiento.entity.*;
import com.mollystore.reabastecimiento.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReabastecimientoService {
    private final OrdenReabastecimientoRepository ordenRepo;
    private final DistribuidorRepository distribuidorRepo;

    public List<OrdenReabastecimiento> findAll() {
        log.debug("Listando todas las ordenes de reabastecimiento");
        return ordenRepo.findAll();
    }

    public OrdenReabastecimiento findById(Long id) {
        log.debug("Buscando orden id={}", id);
        return ordenRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden de reabastecimiento no encontrada con id: " + id));
    }

    public OrdenReabastecimiento crear(OrdenReabastecimiento o) {
        log.info("Creando orden de reabastecimiento para distribuidorId={}", o.getDistribuidor() != null ? o.getDistribuidor().getId() : "null");
        o.setFechaOrden(LocalDateTime.now());
        o.setEstado(EstadoOrden.PENDIENTE);
        return ordenRepo.save(o);
    }

    public OrdenReabastecimiento recibir(Long id) {
        log.info("Marcando orden id={} como RECIBIDA", id);
        OrdenReabastecimiento o = findById(id);
        o.setEstado(EstadoOrden.RECIBIDA);
        return ordenRepo.save(o);
    }

    public OrdenReabastecimiento cancelar(Long id) {
        log.warn("Cancelando orden de reabastecimiento id={}", id);
        OrdenReabastecimiento o = findById(id);
        o.setEstado(EstadoOrden.CANCELADA);
        return ordenRepo.save(o);
    }

    public List<Distribuidor> findAllDistribuidores() {
        return distribuidorRepo.findAll();
    }

    public Distribuidor saveDistribuidor(Distribuidor d) {
        log.info("Guardando distribuidor: {}", d.getNombre());
        return distribuidorRepo.save(d);
    }
}
