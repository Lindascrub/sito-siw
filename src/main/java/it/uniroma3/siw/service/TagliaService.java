package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Taglia;
import it.uniroma3.siw.repository.TagliaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagliaService {
    
    private static final Logger logger = LoggerFactory.getLogger(TagliaService.class);
    private final TagliaRepository tagliaRepository;
    
    public TagliaService(TagliaRepository tagliaRepository) {
        this.tagliaRepository = tagliaRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Taglia> findAllOrdinate() {
        logger.debug("Recupero tutte le taglie ordinate");
        return tagliaRepository.findAllByOrderByOrdineAsc();
    }
    
    @Transactional(readOnly = true)
    public Taglia findById(Long id) {
        return tagliaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Taglia non trovata: " + id));
    }
    
    @Transactional(readOnly = true)
    public Taglia findByNome(String nome) {
        return tagliaRepository.findByNome(nome)
            .orElseThrow(() -> new RuntimeException("Taglia non trovata: " + nome));
    }
    
    @Transactional
    public Taglia salvaTaglia(Taglia taglia) {
        logger.info("Salvataggio taglia: {}", taglia.getNome());
        return tagliaRepository.save(taglia);
    }
}