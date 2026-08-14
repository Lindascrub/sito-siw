package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    Optional<Categoria> findByNome(String nome);
    
    boolean existsByNome(String nome);
    
    List<Categoria> findAllByOrderByNomeAsc();
}