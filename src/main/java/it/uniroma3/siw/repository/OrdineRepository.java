package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface CarrelloRepository extends CrudRepository<Carrello, Long> {
    Optional<Carrello> findByUtente(Utente utente);
}