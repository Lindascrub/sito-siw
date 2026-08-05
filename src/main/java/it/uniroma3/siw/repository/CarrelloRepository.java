package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CarrelloRepository extends JpaRepository<Carrello, Long> {
    
    Optional<Carrello> findByUtente(Utente utente);
    
    @Query("SELECT c FROM Carrello c WHERE c.utente.email = :email")
    Optional<Carrello> findByUtenteEmail(@Param("email") String email);
    
    boolean existsByUtente(Utente utente);
}