package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.RigaCarrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CarrelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CarrelloService {
    
    @Autowired
    private CarrelloRepository carrelloRepository;
    
    @Autowired
    private ProdottoService prodottoService;
    
    @Transactional
    public Carrello getCarrello(Utente utente) {
        Optional<Carrello> carrelloOpt = carrelloRepository.findByUtente(utente);
        if (carrelloOpt.isPresent()) {
            return carrelloOpt.get();
        }
        
        Carrello nuovoCarrello = new Carrello();
        nuovoCarrello.setUtente(utente);
        return carrelloRepository.save(nuovoCarrello);
    }
    
    @Transactional
    public Carrello aggiungiProdotto(Utente utente, Long prodottoId, int quantita) {
        Carrello carrello = getCarrello(utente);
        Prodotto prodotto = prodottoService.getProdotto(prodottoId);
        
        // Cerca se il prodotto è già nel carrello
        Optional<RigaCarrello> rigaEsistente = carrello.getRighe().stream()
            .filter(r -> r.getProdotto().getId().equals(prodottoId))
            .findFirst();
        
        if (rigaEsistente.isPresent()) {
            rigaEsistente.get().setQuantita(rigaEsistente.get().getQuantita() + quantita);
        } else {
            carrello.getRighe().add(new RigaCarrello(prodotto, quantita));
        }
        
        return carrelloRepository.save(carrello);
    }
    
    @Transactional
    public Carrello rimuoviProdotto(Utente utente, Long prodottoId) {
        Carrello carrello = getCarrello(utente);
        carrello.getRighe().removeIf(r -> r.getProdotto().getId().equals(prodottoId));
        return carrelloRepository.save(carrello);
    }
    
    @Transactional
    public Carrello svuotaCarrello(Utente utente) {
        Carrello carrello = getCarrello(utente);
        carrello.getRighe().clear();
        return carrelloRepository.save(carrello);
    }
    @Transactional
    public Carrello aggiornaQuantita(Utente utente, Long prodottoId, int quantita) {
        Carrello carrello = getCarrello(utente);
        
        for (RigaCarrello riga : carrello.getRighe()) {
            if (riga.getProdotto().getId().equals(prodottoId)) {
                if (quantita <= 0) {
                    carrello.getRighe().remove(riga);
                } else {
                    riga.setQuantita(quantita);
                }
                break;
            }
        }
        
        return carrelloRepository.save(carrello);
    }
}