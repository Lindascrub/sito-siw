package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Utente;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OrdineRepository extends CrudRepository<Ordine, Long> {
    List<Ordine> findByUtenteOrderByDataCreazioneDesc(Utente utente);
}