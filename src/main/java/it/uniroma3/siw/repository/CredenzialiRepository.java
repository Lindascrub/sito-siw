package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Credenziali;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CredenzialiRepository extends JpaRepository<Credenziali, Long> {
    
    Optional<Credenziali> findByUsername(String username);
    
    boolean existsByUsername(String username);
}