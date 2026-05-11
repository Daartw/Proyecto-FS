package com.mollystore.inventario.service;

import com.mollystore.inventario.entity.*;
import com.mollystore.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService {
    private final ItemInventarioRepository itemRepo;
    private final MovimientoInventarioRepository movimientoRepo;
    private static final int STOCK_CRITICO = 3;

    public List<ItemInventario> findAll() { return itemRepo.findAll(); }
    public ItemInventario findById(Long id) {
        return itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Item no encontrado: " + id));
    }
    public List<ItemInventario> findByCartaId(Long cartaId) { return itemRepo.findByCartaId(cartaId); }
    public List<ItemInventario> findBajoStock() { return itemRepo.findByCantidadLessThan(STOCK_CRITICO); }
    public ItemInventario save(ItemInventario item) {
        item.setFechaIngreso(LocalDateTime.now());
        return itemRepo.save(item);
    }
    public ItemInventario update(Long id, ItemInventario item) {
        ItemInventario existing = findById(id);
        existing.setCantidad(item.getCantidad());
        existing.setEstado(item.getEstado());
        existing.setUbicacion(item.getUbicacion());
        return itemRepo.save(existing);
    }
    public void descontarStock(Long id, Integer cantidad) {
        ItemInventario item = findById(id);
        if (item.getCantidad() < cantidad) throw new RuntimeException("Stock insuficiente");
        item.setCantidad(item.getCantidad() - cantidad);
        itemRepo.save(item);
        MovimientoInventario mov = MovimientoInventario.builder()
            .item(item).cantidadMovida(cantidad)
            .motivo("VENTA").fecha(LocalDateTime.now()).build();
        movimientoRepo.save(mov);
    }
    public void delete(Long id) { itemRepo.deleteById(id); }
    public List<MovimientoInventario> findAllMovimientos() { return movimientoRepo.findAll(); }
    public MovimientoInventario saveMovimiento(MovimientoInventario m) {
        m.setFecha(LocalDateTime.now());
        return movimientoRepo.save(m);
    }
}
