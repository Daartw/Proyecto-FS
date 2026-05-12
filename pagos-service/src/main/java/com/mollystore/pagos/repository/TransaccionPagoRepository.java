package com.mollystore.pagos.repository;
import com.mollystore.pagos.entity.TransaccionPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TransaccionPagoRepository extends JpaRepository<TransaccionPago, Long> {
    List<TransaccionPago> findByVentaId(Long ventaId);
}
