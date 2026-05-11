package com.mollystore.inventario.repository;
import com.mollystore.inventario.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {}
