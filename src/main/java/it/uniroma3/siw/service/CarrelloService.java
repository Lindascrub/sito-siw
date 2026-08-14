package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.RigaCarrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CarrelloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarrelloService {
    
    private static final Logger logger = LoggerFactory.getLogger(CarrelloService.class);
    private final CarrelloRepository carrelloRepository;
    private final ProdottoService prodottoService;
    private final UtenteService utenteService;
    
    public CarrelloService(CarrelloRepository carrelloRepository,
                           ProdottoService prodottoService,
                           UtenteService utenteService) {
        this.carrelloRepository = carrelloRepository;
        this.prodottoService = prodottoService;
        this.utenteService = utenteService;
    }
    
    @Transactional(readOnly = true)
    public Carrello trovaPerUtente(Long utenteId) {
        Utente utente = utenteService.findById(utenteId);
        return carrelloRepository.findByUtente(utente)
            .orElseGet(() -> {
                Carrello nuovo = new Carrello(utente);
                return carrelloRepository.save(nuovo);
            });
    }
    
    @Transactional
    public Carrello aggiungiProdotto(Long utenteId, Long prodottoId, Integer quantita) {
        return aggiungiProdotto(utenteId, prodottoId, quantita, null, null);
    }
    
    @Transactional
    public Carrello aggiungiProdotto(Long utenteId, Long prodottoId, Integer quantita,
                                     String taglia, String colore) {
        Carrello carrello = trovaPerUtente(utenteId);
        Prodotto prodotto = prodottoService.findById(prodottoId);
        
        if (prodotto.getQuantitaDisponibile() < quantita) {
            throw new RuntimeException("Stock insufficiente per: " + prodotto.getNome());
        }
        
       if ((taglia == null || taglia.isBlank()) && !prodotto.getTaglieDisponibili().isEmpty()) {
            taglia = prodotto.getTaglieDisponibili().get(0);
        }
        if ((colore == null || colore.isBlank()) && !prodotto.getColoriDisponibili().isEmpty()) {
            colore = prodotto.getColoriDisponibili().get(0);
        }
        
        carrello.aggiungiProdotto(prodotto, quantita, taglia, colore);
        logger.info("Aggiunto prodotto {} al carrello", prodotto.getNome());
        return carrelloRepository.save(carrello);
    }
    
    @Transactional
    public Carrello aggiornaQuantita(Long utenteId, Long prodottoId, Integer nuovaQuantita) {
        Carrello carrello = trovaPerUtente(utenteId);
        Prodotto prodotto = prodottoService.findById(prodottoId);
        
        if (nuovaQuantita == null || nuovaQuantita <= 0) {
            carrello.rimuoviProdotto(prodotto);
            return carrelloRepository.save(carrello);
        }
        
        if (prodotto.getQuantitaDisponibile() < nuovaQuantita) {
            throw new RuntimeException("Stock insufficiente per: " + prodotto.getNome());
        }
        
        for (RigaCarrello riga : carrello.getRighe()) {
            if (riga.getProdotto().getId().equals(prodotto.getId())) {
                riga.setQuantita(nuovaQuantita);
                break;
            }
        }
        logger.info("Quantità aggiornata per {}: {}", prodotto.getNome(), nuovaQuantita);
        return carrelloRepository.save(carrello);
    }
    
    @Transactional
    public Carrello rimuoviProdotto(Long utenteId, Long prodottoId) {
        Utente utente = utenteService.findById(utenteId);
        Carrello carrello = trovaPerUtente(utenteId);
        Prodotto prodotto = prodottoService.findById(prodottoId);
        
        carrello.rimuoviProdotto(prodotto);
        logger.info("Rimosso prodotto {} dal carrello", prodotto.getNome());
        return carrelloRepository.save(carrello);
    }
    
    @Transactional
    public void svuotaCarrello(Long utenteId) {
        Carrello carrello = trovaPerUtente(utenteId);
        carrello.svuota();
        carrelloRepository.save(carrello);
        logger.info("Carrello svuotato per utente: {}", utenteId);
    }
    
    
    @Transactional
    public Carrello aggiornaRiga(Long utenteId, Long prodottoId, Integer quantita,
                                 String taglia, String colore) {
        Carrello carrello = trovaPerUtente(utenteId);
        Prodotto prodotto = prodottoService.findById(prodottoId);
        
        if (quantita == null || quantita <= 0) {
            carrello.rimuoviProdotto(prodotto);
            return carrelloRepository.save(carrello);
        }
        
        if (prodotto.getQuantitaDisponibile() < quantita) {
            throw new RuntimeException("Disponibili solo " + prodotto.getQuantitaDisponibile()
                + " pezzi di " + prodotto.getNome());
        }
        
        for (RigaCarrello riga : carrello.getRighe()) {
            if (riga.getProdotto().getId().equals(prodottoId)) {
                riga.setQuantita(quantita);
                if (taglia != null && !taglia.isBlank()) {
                    riga.setTaglia(taglia);
                }
                if (colore != null && !colore.isBlank()) {
                    riga.setColore(colore);
                }
                break;
            }
        }
        return carrelloRepository.save(carrello);
    }
    
    @Transactional
    public Carrello aggiornaDettagli(Long utenteId, Long prodottoId, String taglia, String colore) {
        Carrello carrello = trovaPerUtente(utenteId);
        for (RigaCarrello riga : carrello.getRighe()) {
            if (riga.getProdotto().getId().equals(prodottoId)) {
                riga.setTaglia(taglia);
                riga.setColore(colore);
                break;
            }
        }
        return carrelloRepository.save(carrello);
    }
}