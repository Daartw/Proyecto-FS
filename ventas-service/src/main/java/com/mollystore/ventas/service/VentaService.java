package com.mollystore.ventas.service;

import com.mollystore.ventas.dto.SincronizacionRequestDTO;
import com.mollystore.ventas.dto.SincronizacionResponseDTO;
import com.mollystore.ventas.entity.*;
import com.mollystore.ventas.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepo;
    private final TipoCambioRepository tipoCambioRepo;
    private final WebClient sincronizacionWebClient;

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

    /**
     * Completa la venta y llama a sincronizacion-service via WebClient
     * para descontar el stock de cada ítem vendido.
     */
    public Venta completar(Long id) {
        log.info("Completando venta id={}", id);
        Venta v = findById(id);
        v.setEstado(EstadoVenta.COMPLETADA);
        ventaRepo.save(v);

        // Sincronizar descuento de inventario para cada detalle de la venta
        if (v.getDetalles() != null && !v.getDetalles().isEmpty()) {
            log.info("Iniciando sincronizacion de inventario para {} items de la venta id={}",
                    v.getDetalles().size(), id);

            for (DetalleVenta detalle : v.getDetalles()) {
                sincronizarDescuento(v.getId(), detalle.getItemInventarioId(), detalle.getCantidad());
            }
        } else {
            log.warn("La venta id={} no tiene detalles, no se sincronizara inventario", id);
        }

        return v;
    }

    /**
     * Llama a sincronizacion-service para registrar y ejecutar el descuento de inventario.
     */
    private void sincronizarDescuento(Long ventaId, Long itemId, Integer cantidad) {
        log.info("Llamando a sincronizacion-service: ventaId={}, itemId={}, cantidad={}",
                ventaId, itemId, cantidad);

        SincronizacionRequestDTO request = SincronizacionRequestDTO.builder()
                .ventaId(ventaId)
                .itemInventarioId(itemId)
                .cantidadDescontada(cantidad)
                .origen("ventas-service")
                .build();

        try {
            SincronizacionResponseDTO response = sincronizacionWebClient.post()
                    .uri("/api/sincronizacion/descontar")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(SincronizacionResponseDTO.class)
                    .block();

            if (response != null && "EXITOSO".equals(response.getEstado())) {
                log.info("Sincronizacion exitosa para itemId={}, eventoId={}", itemId, response.getId());
            } else {
                log.warn("Sincronizacion fallida para itemId={}: {}",
                        itemId, response != null ? response.getMensajeError() : "sin respuesta");
            }

        } catch (WebClientResponseException ex) {
            log.error("Error HTTP al llamar sincronizacion-service para itemId={}: status={}, body={}",
                    itemId, ex.getStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Error inesperado al comunicarse con sincronizacion-service para itemId={}: {}",
                    itemId, ex.getMessage());
        }
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
