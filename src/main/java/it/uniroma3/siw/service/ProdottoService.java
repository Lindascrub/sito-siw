package it.uniroma3.siw.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.ProdottoRepository;


@Service
public class ProdottoService {
    
    private ProdottoRepository prodottoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProdottoService.class);
    
    public ProdottoService(ProdottoRepository prodottoRepository) {
        this.prodottoRepository = prodottoRepository;
    }
    
    @Transactional(readOnly = true)
    public Prodotto findById(Long id) {
        logger.debug("Ricerca prodotto con id: {}", id);
        return prodottoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Prodotto> findAllAttivi() {
        logger.info("Recupero tutti i prodotti attivi");
        return prodottoRepository.findByAttivoTrue();
    }
    
    @Transactional(readOnly = true)
    public List<Prodotto> findAllDisponibili() {
        logger.debug("Recupero prodotti disponibili (stock > 0)");
        return prodottoRepository.findProdottiDisponibili();
    }
    
    @Transactional(readOnly = true)
    public List<Prodotto> cercaPerCategoria(Long categoriaId) {
        logger.debug("Ricerca prodotti per categoria: {}", categoriaId);
        return prodottoRepository.findByCategoriaIdAndAttivoTrue(categoriaId);
    }
    
    @Transactional(readOnly = true)
    public List<Prodotto> cercaPerNome(String nome) {
        logger.debug("Ricerca prodotti per nome: {}", nome);
        return prodottoRepository.findByNomeContainingIgnoreCaseAndAttivoTrue(nome);
    }
    @Transactional(readOnly = true)
    public List<Prodotto> cercaAvanzata(String nome, Long categoriaId) {
        logger.debug("Ricerca avanzata: nome={}, categoria={}", nome, categoriaId);
        return prodottoRepository.searchProdotti(nome, categoriaId);
    }
    
    @Transactional(readOnly = true)
    public List<Prodotto> trovaInRangePrezzo(Double min, Double max) {
        logger.debug("Ricerca prodotti in range prezzo: {}-{}", min, max);
        return prodottoRepository.findByPrezzoBetweenAndAttivoTrue(min, max);
    }
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Prodotto salvaProdotto(Prodotto prodotto) {
        // Regola di business: verifica unicità codice modello
        if (prodotto.getId() == null && prodotto.getCodiceModello() != null) {
            if (prodottoRepository.existsByCodiceModello(prodotto.getCodiceModello())) {
                throw new RuntimeException("Codice modello già esistente: " + prodotto.getCodiceModello());
            }
        }
        
        // Regola di business: verifica unicità nome
        if (prodotto.getId() == null && prodottoRepository.existsByNomeAndAttivoTrue(prodotto.getNome())) {
            throw new RuntimeException("Prodotto con nome già esistente: " + prodotto.getNome());
        }
        
        logger.info("Salvataggio prodotto: {}", prodotto.getNome());
        return prodottoRepository.save(prodotto);
    }
    
    @Transactional
    public Prodotto aggiornaQuantita(Long id, Integer nuovaQuantita) {
        Prodotto prodotto = findById(id);
        if (nuovaQuantita < 0) {
            throw new RuntimeException("La quantità non può essere negativa");
        }
        prodotto.setQuantitaDisponibile(nuovaQuantita);
        logger.info("Stock aggiornato per {}: {}", prodotto.getNome(), nuovaQuantita);
        return prodottoRepository.save(prodotto);
    }
    
    @Transactional
    public void disattivaProdotto(Long id) {
        Prodotto prodotto = findById(id);
        prodotto.setAttivo(false);
        prodottoRepository.save(prodotto);
        logger.info("Prodotto disattivato: {}", prodotto.getNome());
    }
    
    @Transactional
    public void attivaProdotto(Long id) {
        Prodotto prodotto = findById(id);
        prodotto.setAttivo(true);
        prodottoRepository.save(prodotto);
        logger.info("Prodotto riattivato: {}", prodotto.getNome());
    }
}