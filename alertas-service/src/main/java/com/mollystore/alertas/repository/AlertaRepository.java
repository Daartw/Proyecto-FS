package com.mollystore.alertas.repository;
import com.mollystore.alertas.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByDestinatarioId(Long destinatarioId);
}
