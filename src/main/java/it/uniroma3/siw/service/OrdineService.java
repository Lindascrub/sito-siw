package it.uniroma3.siw.service;

import it.uniroma3.siw.dto.ArticoloCarrelloDTO;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.RigaOrdine;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.OrdineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;

@Service
@Transactional
public class OrdineService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrdineService.class);
    private final OrdineRepository ordineRepository;
    private final ProdottoService prodottoService;
    private final UtenteService utenteService;
    
    public OrdineService(OrdineRepository ordineRepository,
                         ProdottoService prodottoService,
                         UtenteService utenteService) {
        this.ordineRepository = ordineRepository;
        this.prodottoService = prodottoService;
        this.utenteService = utenteService;
    }
    
    // ✅ Usa ArticoloCarrelloDTO invece di ArticoloCarrello
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ordine creaOrdine(Long utenteId, List<ArticoloCarrelloDTO> articoli,
                             String indirizzo, String citta, String cap) {
        
        logger.info("Creazione ordine per utente: {}", utenteId);
        
        Utente utente = utenteService.findById(utenteId);
        Ordine ordine = new Ordine(utente);
        ordine.setIndirizzoSpedizione(indirizzo);
        ordine.setCittaSpedizione(citta);
        ordine.setCodPostaleSpedizione(cap);
        
        for (ArticoloCarrelloDTO articolo : articoli) {
            Prodotto prodotto = prodottoService.findById(articolo.getProdottoId());
            
            // Verifica stock
            if (prodotto.getQuantitaDisponibile() < articolo.getQuantita()) {
                throw new RuntimeException(
                    "Stock insufficiente per: " + prodotto.getNome() +
                    ". Disponibili: " + prodotto.getQuantitaDisponibile()
                );
            }
            
            RigaOrdine riga = new RigaOrdine();
            riga.setProdotto(prodotto);
            riga.setQuantita(articolo.getQuantita());
            riga.setPrezzoUnitario(prodotto.getPrezzo());
            riga.setTaglia(articolo.getTaglia());
            riga.setColore(articolo.getColore());
            riga.setOrdine(ordine);
            
            ordine.getRighe().add(riga);
            
            // Aggiorna stock
            prodotto.setQuantitaDisponibile(
                prodotto.getQuantitaDisponibile() - articolo.getQuantita()
            );
            prodottoService.salvaProdotto(prodotto);
        }
        
        ordine.calcolaTotale();
        Ordine saved = ordineRepository.save(ordine);
        logger.info("Ordine creato con id: {}", saved.getId());
        
        return saved;
    }
}