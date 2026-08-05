package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.RigaCarrello;

public interface RigaCarrelloRepository extends JpaRepository<RigaCarrello, Long> {
    
    List<RigaCarrello> findByCarrello(Carrello carrello);
}