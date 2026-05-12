package com.mollystore.ventas.service;

import com.mollystore.ventas.entity.*;
import com.mollystore.ventas.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {
    private final VentaRepository ventaRepo;
    private final TipoCambioRepository tipoCambioRepo;

    public List<Venta> findAll() {
        log.debug("Listando todas las ventas");
        return ventaRepo.findAll();
    }

    public Venta findById(Long id) {
        log.debug("Buscando venta id={}", id);
        return ventaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
    }

    public List<Venta> findByCliente(Long clienteId) {
        return ventaRepo.findByClienteId(clienteId);
    }

    public Venta crear(Venta venta) {
        log.info("Creando nueva venta para clienteId={} en moneda={}", venta.getClienteId(), venta.getMoneda());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado(EstadoVenta.PENDIENTE);
        return ventaRepo.save(venta);
    }

    public Venta completar(Long id) {
        log.info("Completando venta id={}", id);
        Venta v = findById(id);
        v.setEstado(EstadoVenta.COMPLETADA);
        return ventaRepo.save(v);
    }

    public Venta anular(Long id) {
        log.warn("Anulando venta id={}", id);
        Venta v = findById(id);
        v.setEstado(EstadoVenta.ANULADA);
        return ventaRepo.save(v);
    }

    public TipoCambio getTipoCambioActual() {
        return tipoCambioRepo.findTopByMonedaOrigenAndMonedaDestinoOrderByFechaActualizacionDesc("USD", "CLP")
            .orElseThrow(() -> new RuntimeException("No hay tipo de cambio USD/CLP configurado"));
    }

    public TipoCambio saveTipoCambio(TipoCambio tc) {
        log.info("Actualizando tipo de cambio {}/{}: {}", tc.getMonedaOrigen(), tc.getMonedaDestino(), tc.getValorCambio());
        tc.setFechaActualizacion(LocalDateTime.now());
        return tipoCambioRepo.save(tc);
    }

    public Double convertir(Double monto, String moneda) {
        TipoCambio tc = getTipoCambioActual();
        double resultado = moneda.equalsIgnoreCase("USD") ? monto * tc.getValorCambio() : monto / tc.getValorCambio();
        log.debug("Conversion: {} {} = {}", monto, moneda, resultado);
        return resultado;
    }
}
