package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.RigaOrdine;

public interface RigaOrdineRepository extends JpaRepository<RigaOrdine, Long> {
    
    List<RigaOrdine> findByOrdine(Ordine ordine);

    boolean existsByProdottoId(Long prodottoId);
    
    @Query("SELECT r.prodotto.id, SUM(r.quantita) as totale " +
           "FROM RigaOrdine r " +
           "GROUP BY r.prodotto.id " +
           "ORDER BY totale DESC")
    List<Object[]> findProdottiPiuVenduti();
}