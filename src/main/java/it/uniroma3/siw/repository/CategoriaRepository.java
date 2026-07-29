package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    // Questo metodo trova le categorie per nome (esatto)
    List<Categoria> findByNome(String nome);
    
    // Questo metodo trova le categorie che contengono una certa parola nel nome
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
}
