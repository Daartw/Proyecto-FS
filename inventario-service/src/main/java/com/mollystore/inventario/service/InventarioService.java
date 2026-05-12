package com.mollystore.inventario.service;

import com.mollystore.inventario.entity.*;
import com.mollystore.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventarioService {
    private final ItemInventarioRepository itemRepo;
    private final MovimientoInventarioRepository movimientoRepo;
    private static final int STOCK_CRITICO = 3;

    public List<ItemInventario> findAll() {
        log.debug("Listando todos los items de inventario");
        return itemRepo.findAll();
    }

    public ItemInventario findById(Long id) {
        log.debug("Buscando item inventario id={}", id);
        return itemRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Item de inventario no encontrado con id: " + id));
    }

    public List<ItemInventario> findByCartaId(Long cartaId) {
        return itemRepo.findByCartaId(cartaId);
    }

    public List<ItemInventario> findBajoStock() {
        log.debug("Consultando items con stock bajo (< {})", STOCK_CRITICO);
        return itemRepo.findByCantidadLessThan(STOCK_CRITICO);
    }

    public ItemInventario save(ItemInventario item) {
        log.info("Ingresando nuevo item de inventario para cartaId={}", item.getCartaId());
        item.setFechaIngreso(LocalDateTime.now());
        return itemRepo.save(item);
    }

    public ItemInventario update(Long id, ItemInventario item) {
        log.info("Actualizando item inventario id={}", id);
        ItemInventario existing = findById(id);
        existing.setCantidad(item.getCantidad());
        existing.setEstado(item.getEstado());
        existing.setUbicacion(item.getUbicacion());
        return itemRepo.save(existing);
    }

    public void descontarStock(Long id, Integer cantidad) {
        log.info("Descontando {} unidades del item id={}", cantidad, id);
        ItemInventario item = findById(id);
        if (item.getCantidad() < cantidad) {
            log.warn("Stock insuficiente para item id={}: disponible={}, solicitado={}", id, item.getCantidad(), cantidad);
            throw new RuntimeException("Stock insuficiente. Disponible: " + item.getCantidad() + ", Solicitado: " + cantidad);
        }
        item.setCantidad(item.getCantidad() - cantidad);
        itemRepo.save(item);
        MovimientoInventario mov = MovimientoInventario.builder()
            .item(item).cantidadMovida(cantidad)
            .motivo("VENTA").fecha(LocalDateTime.now()).build();
        movimientoRepo.save(mov);
        log.debug("Stock descontado correctamente. Nuevo stock: {}", item.getCantidad());
    }

    public void delete(Long id) {
        log.info("Eliminando item inventario id={}", id);
        itemRepo.deleteById(id);
    }

    public List<MovimientoInventario> findAllMovimientos() {
        return movimientoRepo.findAll();
    }

    public MovimientoInventario saveMovimiento(MovimientoInventario m) {
        log.info("Registrando movimiento de inventario: motivo={}", m.getMotivo());
        m.setFecha(LocalDateTime.now());
        return movimientoRepo.save(m);
    }
}
