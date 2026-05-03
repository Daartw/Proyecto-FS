package com.mollystore.buylist.repository;
import com.mollystore.buylist.entity.SolicitudBuylist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SolicitudBuylistRepository extends JpaRepository<SolicitudBuylist, Long> {
    List<SolicitudBuylist> findByClienteId(Long clienteId);
}
