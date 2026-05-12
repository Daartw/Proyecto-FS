package com.mollystore.jugadores.repository;
import com.mollystore.jugadores.entity.MazoPreferido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MazoPreferidoRepository extends JpaRepository<MazoPreferido, Long> {
    List<MazoPreferido> findByJugadorId(Long jugadorId);
}
