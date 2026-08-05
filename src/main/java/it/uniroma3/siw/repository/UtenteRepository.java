package it.uniroma3.siw.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
	Optional<Utente> findByEmail(String email);
	boolean existsByEmail(String email);
	@Query("SELECT u FROM Utente u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')) " +
	           "OR LOWER(u.cognome) LIKE LOWER(CONCAT('%', :cognome, '%'))")
	    List<Utente> searchUtenti(@Param("nome") String nome, @Param("cognome") String cognome);
	
}
