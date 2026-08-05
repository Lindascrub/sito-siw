package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Prodotto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long>, PagingAndSortingRepository<Prodotto, Long> {
    
    // Paginazione base
    Page<Prodotto> findAll(Pageable pageable);
    
    // Filtri
    Page<Prodotto> findByCategoriaNome(String categoriaNome, Pageable pageable);
    Page<Prodotto> findByPrezzoBetween(Double min, Double max, Pageable pageable);
    Page<Prodotto> findByPrezzoGreaterThanEqual(Double min, Pageable pageable);
    Page<Prodotto> findByPrezzoLessThanEqual(Double max, Pageable pageable);
    Page<Prodotto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    
    // Query senza paginazione
    List<Prodotto> findByCategoriaId(Long categoriaId);
n    List<Prodotto> findByNomeContainingIgnoreCase(String nome);
    List<Prodotto> findByPrezzoBetween(Double min, Double max);
}