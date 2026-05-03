package com.mollystore.alertas.repository;
import com.mollystore.alertas.entity.SuscripcionCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SuscripcionCartaRepository extends JpaRepository<SuscripcionCarta, Long> {
    List<SuscripcionCarta> findByJugadorIdAndActivaTrue(Long jugadorId);
}
