package com.mollystore.ventas.service;
import com.mollystore.ventas.entity.*;
import com.mollystore.ventas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class VentaService {
    private final VentaRepository ventaRepo;
    private final TipoCambioRepository tipoCambioRepo;

    public List<Venta> findAll() { return ventaRepo.findAll(); }
    public Venta findById(Long id) {
        return ventaRepo.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));
    }
    public List<Venta> findByCliente(Long clienteId) { return ventaRepo.findByClienteId(clienteId); }
    public Venta crear(Venta venta) {
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado(EstadoVenta.PENDIENTE);
        return ventaRepo.save(venta);
    }
    public Venta completar(Long id) {
        Venta v = findById(id); v.setEstado(EstadoVenta.COMPLETADA); return ventaRepo.save(v);
    }
    public Venta anular(Long id) {
        Venta v = findById(id); v.setEstado(EstadoVenta.ANULADA); return ventaRepo.save(v);
    }
    public TipoCambio getTipoCambioActual() {
        return tipoCambioRepo.findTopByMonedaOrigenAndMonedaDestinoOrderByFechaActualizacionDesc("USD","CLP")
            .orElseThrow(() -> new RuntimeException("No hay tipo de cambio configurado"));
    }
    public TipoCambio saveTipoCambio(TipoCambio tc) {
        tc.setFechaActualizacion(LocalDateTime.now()); return tipoCambioRepo.save(tc);
    }
    public Double convertir(Double monto, String moneda) {
        TipoCambio tc = getTipoCambioActual();
        return moneda.equalsIgnoreCase("USD") ? monto * tc.getValorCambio() : monto / tc.getValorCambio();
    }
}
