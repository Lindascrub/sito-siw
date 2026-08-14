package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Prodotto;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    
    List<Prodotto> findByAttivoTrue();
    
    List<Prodotto> findByCategoriaIdAndAttivoTrue(Long categoriaId);
    
    List<Prodotto> findByNomeContainingIgnoreCaseAndAttivoTrue(String nome);
    
    List<Prodotto> findByPrezzoBetweenAndAttivoTrue(Double min, Double max);
    
    List<Prodotto> findByQuantitaDisponibileGreaterThanAndAttivoTrue(Integer quantita);
    
    // Query 
    @Query("SELECT p FROM Prodotto p WHERE p.quantitaDisponibile > 0 AND p.attivo = true")
    List<Prodotto> findProdottiDisponibili();
    
    @Query("SELECT p FROM Prodotto p WHERE " +
           "(:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:categoriaId IS NULL OR p.categoria.id = :categoriaId) AND " +
           "p.attivo = true")
    List<Prodotto> searchProdotti(@Param("nome") String nome, 
                                  @Param("categoriaId") Long categoriaId);
    

    boolean existsByCodiceModello(String codiceModello);
    
    boolean existsByNomeAndAttivoTrue(String nome);
}