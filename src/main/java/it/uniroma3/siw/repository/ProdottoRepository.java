package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.StatoOrdine;
import it.uniroma3.siw.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
    
    List<Ordine> findByUtenteOrderByDataOrdineDesc(Utente utente);
    
    List<Ordine> findByStato(StatoOrdine stato);
    
    List<Ordine> findByDataOrdineBetween(LocalDateTime inizio, LocalDateTime fine);
    
    @Query("SELECT o FROM Ordine o WHERE o.stato = :stato AND o.dataOrdine < :data")
    List<Ordine> findOldOrdersByState(@Param("stato") StatoOrdine stato, 
                                      @Param("data") LocalDateTime data);
}