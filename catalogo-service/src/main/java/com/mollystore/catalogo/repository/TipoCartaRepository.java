package com.mollystore.catalogo.repository;
import com.mollystore.catalogo.entity.TipoCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TipoCartaRepository extends JpaRepository<TipoCarta, Long> {}
