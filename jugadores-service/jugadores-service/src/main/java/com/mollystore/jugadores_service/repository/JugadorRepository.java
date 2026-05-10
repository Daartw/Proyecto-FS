package com.mollystore.jugadores_service.repository;

import com.mollystore.jugadores_service.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    Optional<Jugador> findByEmail(String email);
    boolean existsByEmail(String email);
}
