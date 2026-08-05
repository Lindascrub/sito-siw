package it.uniroma3.siw.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Taglia;

public interface TagliaRepository extends JpaRepository<Taglia, Long> {
    
    Optional<Taglia> findByNome(String nome);
    
    List<Taglia> findAllByOrderByOrdineAsc();
}