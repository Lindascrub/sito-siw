package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.RigaCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RigaCarrelloRepository extends JpaRepository<RigaCarrello, Long> {
    
    // ✅ Se vuoi trovare le righe di un carrello, usa JPQL
    @Query("SELECT r FROM RigaCarrello r WHERE r.carrello.id = :carrelloId")
    List<RigaCarrello> findByCarrelloId(@Param("carrelloId") Long carrelloId);
    
    // ✅ Oppure, se il campo esiste, usa query derivation
    // List<RigaCarrello> findByCarrello(Carrello carrello); 
    // → NON FUNZIONA perché 'carrello' non è dichiarato in RigaCarrello!
}