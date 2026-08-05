package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaService {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);
    private final CategoriaRepository categoriaRepository;
    
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        logger.debug("Recupero tutte le categorie");
        return categoriaRepository.findAllByOrderByNomeAsc();
    }
    
    @Transactional(readOnly = true)
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria non trovata: " + id));
    }
    
    @Transactional(readOnly = true)
    public Categoria findByNome(String nome) {
        return categoriaRepository.findByNome(nome)
            .orElseThrow(() -> new RuntimeException("Categoria non trovata: " + nome));
    }
    
    @Transactional
    public Categoria salvaCategoria(Categoria categoria) {
        if (categoria.getId() == null && categoriaRepository.existsByNome(categoria.getNome())) {
            throw new RuntimeException("Categoria già esistente: " + categoria.getNome());
        }
        logger.info("Salvataggio categoria: {}", categoria.getNome());
        return categoriaRepository.save(categoria);
    }
    
    @Transactional
    public void eliminaCategoria(Long id) {
        Categoria categoria = findById(id);
        categoriaRepository.delete(categoria);
        logger.info("Categoria eliminata: {}", categoria.getNome());
    }
}