package com.mollystore.catalogo.repository;

import com.mollystore.catalogo.entity.Rareza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RarezaRepository extends JpaRepository<Rareza, Long> {

}
