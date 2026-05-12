package com.mollystore.sincronizacion.repository;
import com.mollystore.sincronizacion.entity.EventoSincronizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EventoSincronizacionRepository extends JpaRepository<EventoSincronizacion, Long> {
    List<EventoSincronizacion> findByVentaId(Long ventaId);
}
