package it.uniroma3.siw.service;

import it.uniroma3.siw.model.*;
import it.uniroma3.siw.repository.OrdineRepository;
import it.uniroma3.siw.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdineService {
    
    @Autowired
    private OrdineRepository ordineRepository;
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private CarrelloService carrelloService;
    
    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniUtente(Utente utente) {
        return ordineRepository.findByUtenteOrderByDataCreazioneDesc(utente);
    }
    
    @Transactional(readOnly = true)
    public Ordine getOrdine(Long id) {  // ← AGGIUNTO!
        return ordineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ordine non trovato con id: " + id));
    }
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ordine creaOrdine(Utente utente, Carrello carrello) {
        // Verifica disponibilità prodotti
        for (RigaCarrello rigaCarrello : carrello.getRighe()) {
            Prodotto prodotto = rigaCarrello.getProdotto();
            if (prodotto.getQuantitaDisponibile() < rigaCarrello.getQuantita()) {
                throw new RuntimeException("Prodotto non disponibile: " + prodotto.getNome());
            }
        }
        
        // Crea l'ordine
        Ordine ordine = new Ordine();
        ordine.setUtente(utente);
        ordine.setDataCreazione(LocalDateTime.now());
        ordine.setStato(StatoOrdine.CREATO);
        
        double totale = 0;
        
        // Crea le righe ordine e aggiorna la disponibilità
        for (RigaCarrello rigaCarrello : carrello.getRighe()) {
            Prodotto prodotto = rigaCarrello.getProdotto();
            
            // Aggiorna disponibilità
            prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile() - rigaCarrello.getQuantita());
            prodottoRepository.save(prodotto);
            
            // Crea riga ordine
            RigaOrdine rigaOrdine = new RigaOrdine(
                prodotto,
                rigaCarrello.getQuantita(),
                prodotto.getPrezzo()
            );
            rigaOrdine.setOrdine(ordine);
            ordine.getRighe().add(rigaOrdine);
            
            totale += rigaCarrello.getQuantita() * prodotto.getPrezzo();
        }
        
        ordine.setTotale(totale);
        
        // Svuota il carrello
        carrelloService.svuotaCarrello(utente);
        
        return ordineRepository.save(ordine);
    }
}