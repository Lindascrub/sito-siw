package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Prodotto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long>, PagingAndSortingRepository<Prodotto, Long> {
    
    // Query con paginazione
    Page<Prodotto> findAll(Pageable pageable);
    Page<Prodotto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Prodotto> findByCategoriaId(Long categoriaId, Pageable pageable);
    
    // Query senza paginazione
    List<Prodotto> findByCategoriaId(Long categoriaId);
    List<Prodotto> findByNomeContainingIgnoreCase(String nome);
    List<Prodotto> findByPrezzoBetween(Double min, Double max);
}