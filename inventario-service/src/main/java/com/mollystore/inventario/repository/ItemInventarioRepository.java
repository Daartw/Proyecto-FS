package com.mollystore.inventario.repository;
import com.mollystore.inventario.entity.ItemInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {
    List<ItemInventario> findByCartaId(Long cartaId);
    List<ItemInventario> findByCantidadLessThan(Integer cantidad);
}
