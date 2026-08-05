package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.repository.CategoriaRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    
    @Transactional(readOnly = true)
    public Optional<Categoria> getCategoria(Long id) {
        return categoriaRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Categoria> getAllCategorie() {
        return categoriaRepository.findAll();
    }
    
    @Transactional
    public Categoria saveCategoria(Categoria categoria) {
        // Validazioni di business per CATEGORIA
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new RuntimeException("Categoria già esistente: " + categoria.getNome());
        }
        return categoriaRepository.save(categoria);
    }
    
    @Transactional
    public void deleteCategoria(Long id) {
        // Validazione: non eliminare se ha prodotti associati
        if (prodottoRepository.existsByCategoriaId(id)) {  // ← Attenzione! Qua serve ProdottoRepository
            throw new RuntimeException("Non puoi eliminare una categoria con prodotti associati");
        }
        categoriaRepository.deleteById(id);
    }
}