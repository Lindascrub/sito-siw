package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.RecensioneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecensioneService {
    
    private static final Logger logger = LoggerFactory.getLogger(RecensioneService.class);
    private final RecensioneRepository recensioneRepository;
    private final ProdottoService prodottoService;
    private final UtenteService utenteService;
    
    public RecensioneService(RecensioneRepository recensioneRepository,
                             ProdottoService prodottoService,
                             UtenteService utenteService) {
        this.recensioneRepository = recensioneRepository;
        this.prodottoService = prodottoService;
        this.utenteService = utenteService;
    }
    
    @Transactional(readOnly = true)
    public List<Recensione> trovaPerProdotto(Long prodottoId) {
        Prodotto prodotto = prodottoService.findById(prodottoId);
        return recensioneRepository.findByProdottoOrderByDataCreazioneDesc(prodotto);
    }
    
    @Transactional(readOnly = true)
    public List<Recensione> trovaPerUtente(Long utenteId) {
        Utente utente = utenteService.findById(utenteId);
        return recensioneRepository.findByUtenteOrderByDataCreazioneDesc(utente);
    }
    
    @Transactional
    public Recensione salvaRecensione(Long utenteId, Long prodottoId, 
                                      String titolo, String testo, int valutazione) {
        
        Utente utente = utenteService.findById(utenteId);
        Prodotto prodotto = prodottoService.findById(prodottoId);
        
        // Verifica che l'utente non abbia già recensito questo prodotto
        if (recensioneRepository.existsByUtenteAndProdotto(utente, prodotto)) {
            throw new RuntimeException("Hai già recensito questo prodotto");
        }
        
        Recensione recensione = new Recensione();
        recensione.setUtente(utente);
        recensione.setProdotto(prodotto);
        recensione.setTitolo(titolo);
        recensione.setTesto(testo);
        recensione.setValutazione(valutazione);
        
        logger.info("Nuova recensione per prodotto: {} da utente: {}", prodotto.getNome(), utente.getEmail());
        return recensioneRepository.save(recensione);
    }
}