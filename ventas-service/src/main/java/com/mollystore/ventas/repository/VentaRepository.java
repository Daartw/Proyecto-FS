package com.mollystore.ventas.repository;
import com.mollystore.ventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByClienteId(Long clienteId);
}
