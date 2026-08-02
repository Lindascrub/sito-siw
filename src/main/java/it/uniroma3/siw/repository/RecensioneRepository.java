package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Recensione;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {
    List<Recensione> findByProdottoIdOrderByDataCreazioneDesc(Long prodottoId);
    List<Recensione> findByUtenteId(Long utenteId);
}