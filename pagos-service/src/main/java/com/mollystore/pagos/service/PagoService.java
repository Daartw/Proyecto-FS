package com.mollystore.pagos.service;

import com.mollystore.pagos.entity.*;
import com.mollystore.pagos.repository.TransaccionPagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagoService {
    private final TransaccionPagoRepository repo;

    public TransaccionPago iniciar(TransaccionPago t) {
        log.info("Iniciando pago para ventaId={}, metodo={}, monto={}", t.getVentaId(), t.getMetodoPago(), t.getMonto());
        t.setEstado(EstadoPago.PENDIENTE);
        t.setFechaTransaccion(LocalDateTime.now());
        t.setReferenciaPasarela(UUID.randomUUID().toString());
        return repo.save(t);
    }

    public TransaccionPago findById(Long id) {
        log.debug("Buscando transaccion id={}", id);
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Transaccion de pago no encontrada con id: " + id));
    }

    public List<TransaccionPago> findByVenta(Long ventaId) {
        return repo.findByVentaId(ventaId);
    }

    public TransaccionPago confirmar(Long id) {
        log.info("Confirmando pago id={}", id);
        TransaccionPago t = findById(id);
        t.setEstado(EstadoPago.APROBADO);
        t.setCodigoAutorizacion("AUTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return repo.save(t);
    }

    public TransaccionPago reembolsar(Long id) {
        log.warn("Procesando reembolso para pago id={}", id);
        TransaccionPago t = findById(id);
        t.setEstado(EstadoPago.REEMBOLSADO);
        return repo.save(t);
    }
}
