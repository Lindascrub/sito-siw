package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long> {
    
    // Trova prodotti per categoria
    List<Prodotto> findByCategoriaId(Long categoriaId);
    
    // Trova prodotti che contengono una parola nel nome
    List<Prodotto> findByNomeContainingIgnoreCase(String nome);
    
    // Trova prodotti con prezzo tra due valori
    List<Prodotto> findByPrezzoBetween(Double min, Double max);
}