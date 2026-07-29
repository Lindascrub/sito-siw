package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdottoService {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Transactional(readOnly = true)
    public List<Prodotto> getAllProdotti() {
        return (List<Prodotto>) prodottoRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Prodotto getProdotto(Long id) {
        Optional<Prodotto> prodotto = prodottoRepository.findById(id);
        if (prodotto.isPresent()) {
            return prodotto.get();
        }
        throw new RuntimeException("Prodotto non trovato con id: " + id);
    }
    
    @Transactional
    public Prodotto saveProdotto(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }
    
    @Transactional
    public void deleteProdotto(Long id) {
        prodottoRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Prodotto> getProdottiByCategoria(Long categoriaId) {
        return prodottoRepository.findByCategoriaId(categoriaId);
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return prodottoRepository.existsById(id);
    }
}