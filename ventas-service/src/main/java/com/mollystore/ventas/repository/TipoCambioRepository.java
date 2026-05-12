package com.mollystore.ventas.repository;
import com.mollystore.ventas.entity.TipoCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface TipoCambioRepository extends JpaRepository<TipoCambio, Long> {
    Optional<TipoCambio> findTopByMonedaOrigenAndMonedaDestinoOrderByFechaActualizacionDesc(String origen, String destino);
}
