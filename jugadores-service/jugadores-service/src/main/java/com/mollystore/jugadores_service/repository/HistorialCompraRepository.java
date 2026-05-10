package com.mollystore.jugadores_service.repository;

import com.mollystore.jugadores_service.model.HistorialCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface HistorialCompraRepository extends JpaRepository<HistorialCompra, Long> {


    List<HistorialCompra> findByJugadorIdOrderByFechaCompraDesc(Long jugadorId);


    @Query("SELECT COALESCE(SUM(h.puntosGenerados), 0) FROM HistorialCompra h WHERE h.jugador.id = :jugadorId")
    Integer sumPuntosGeneradosByJugadorId(@Param("jugadorId") Long jugadorId);


    List<HistorialCompra> findByJugadorIdAndTipoCompra(Long jugadorId, HistorialCompra.TipoCompra tipoCompra);
}
