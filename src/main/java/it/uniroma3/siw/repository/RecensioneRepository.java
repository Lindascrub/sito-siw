package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    
    List<Recensione> findByProdottoOrderByDataCreazioneDesc(Prodotto prodotto);
    
    List<Recensione> findByUtenteOrderByDataCreazioneDesc(Utente utente);
    
    boolean existsByUtenteAndProdotto(Utente utente, Prodotto prodotto);
}