package com.mollystore.logistica.repository;
import com.mollystore.logistica.entity.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByVentaId(Long ventaId);
    Optional<Envio> findByCodigoSeguimiento(String codigo);
}
