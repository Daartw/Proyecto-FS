package com.mollystore.reabastecimiento.service;
import com.mollystore.reabastecimiento.entity.*;
import com.mollystore.reabastecimiento.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class ReabastecimientoService {
    private final OrdenReabastecimientoRepository ordenRepo;
    private final DistribuidorRepository distribuidorRepo;

    public List<OrdenReabastecimiento> findAll() { return ordenRepo.findAll(); }
    public OrdenReabastecimiento findById(Long id) {
        return ordenRepo.findById(id).orElseThrow(() -> new RuntimeException("Orden no encontrada: " + id));
    }
    public OrdenReabastecimiento crear(OrdenReabastecimiento o) {
        o.setFechaOrden(LocalDateTime.now());
        o.setEstado(EstadoOrden.PENDIENTE);
        return ordenRepo.save(o);
    }
    public OrdenReabastecimiento recibir(Long id) {
        OrdenReabastecimiento o = findById(id); o.setEstado(EstadoOrden.RECIBIDA); return ordenRepo.save(o);
    }
    public OrdenReabastecimiento cancelar(Long id) {
        OrdenReabastecimiento o = findById(id); o.setEstado(EstadoOrden.CANCELADA); return ordenRepo.save(o);
    }
    public List<Distribuidor> findAllDistribuidores() { return distribuidorRepo.findAll(); }
    public Distribuidor saveDistribuidor(Distribuidor d) { return distribuidorRepo.save(d); }
}
