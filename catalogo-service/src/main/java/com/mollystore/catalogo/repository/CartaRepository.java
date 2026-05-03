package com.mollystore.catalogo.repository;

import com.mollystore.catalogo.entity.Carta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> {
    List<Carta> findByNombreEspanolContainingIgnoreCaseOrNombreInglesContainingIgnoreCase(String es, String en);
    List<Carta> findByExpansionId(Long expansionId);
}
