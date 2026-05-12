package com.mollystore.pagos.service;
import com.mollystore.pagos.entity.*;
import com.mollystore.pagos.repository.TransaccionPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class PagoService {
    private final TransaccionPagoRepository repo;

    public TransaccionPago iniciar(TransaccionPago t) {
        t.setEstado(EstadoPago.PENDIENTE);
        t.setFechaTransaccion(LocalDateTime.now());
        t.setReferenciaPasarela(UUID.randomUUID().toString());
        return repo.save(t);
    }
    public TransaccionPago findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Transacción no encontrada: " + id));
    }
    public List<TransaccionPago> findByVenta(Long ventaId) { return repo.findByVentaId(ventaId); }
    public TransaccionPago confirmar(Long id) {
        TransaccionPago t = findById(id);
        t.setEstado(EstadoPago.APROBADO);
        t.setCodigoAutorizacion("AUTH-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        return repo.save(t);
    }
    public TransaccionPago reembolsar(Long id) {
        TransaccionPago t = findById(id);
        t.setEstado(EstadoPago.REEMBOLSADO);
        return repo.save(t);
    }
}
