package com.mollystore.jugadores.repository;
import com.mollystore.jugadores.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
public interface JugadorRepository extends JpaRepository<Jugador, Long> {}
