package it.uniroma3.siw.service;

import it.uniroma3.siw.dto.ArticoloCarrelloDto;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.RigaOrdine;
import it.uniroma3.siw.model.StatoOrdine;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.OrdineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import java.time.LocalDateTime;
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
    
    // =============================================
    // 🔹 METODI CHE TI SERVONO (AGGIUNGI QUESTI!)
    // =============================================
    
    @Transactional(readOnly = true)
    public Ordine findById(Long id) {
        logger.debug("Ricerca ordine con id: {}", id);
        return ordineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ordine non trovato con id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Ordine> trovaPerUtente(Long utenteId) {
        logger.debug("Ricerca ordini per utente: {}", utenteId);
        Utente utente = utenteService.findById(utenteId);
        return ordineRepository.findByUtenteOrderByDataOrdineDesc(utente);
    }
    
    @Transactional(readOnly = true)
    public List<Ordine> trovaPerStato(StatoOrdine stato) {
        logger.debug("Ricerca ordini per stato: {}", stato);
        return ordineRepository.findByStato(stato);
    }
    
    @Transactional(readOnly = true)
    public List<Ordine> trovaPerUtente(Utente utente) {
        logger.debug("Ricerca ordini per utente: {}", utente.getEmail());
        return ordineRepository.findByUtenteOrderByDataOrdineDesc(utente);
    }
    
    // =============================================
    // 🔹 CREAZIONE ORDINE
    // =============================================
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ordine creaOrdine(Long utenteId, List<ArticoloCarrelloDto> articoli,
                             String indirizzo, String citta, String cap) {
        
        logger.info("Creazione ordine per utente: {}", utenteId);
        
        Utente utente = utenteService.findById(utenteId);
        Ordine ordine = new Ordine(utente);
        ordine.setIndirizzoSpedizione(indirizzo);
        ordine.setCittaSpedizione(citta);
        ordine.setCodPostaleSpedizione(cap);
        
        for (ArticoloCarrelloDto articolo : articoli) {
            Prodotto prodotto = prodottoService.findById(articolo.getProdottoId());
            
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
    
    // =============================================
    // 🔹 CONFERMA PAGAMENTO
    // =============================================
    
    @Transactional
    public Ordine confermaPagamento(Long ordineId, String metodoPagamento) {
        Ordine ordine = findById(ordineId);
        
        if (ordine.getStato() != StatoOrdine.CREATO) {
            throw new RuntimeException("L'ordine non è in stato CREATO");
        }
        
        ordine.setStato(StatoOrdine.PAGATO);
        ordine.setDataPagamento(LocalDateTime.now());
        ordine.setMetodoPagamento(metodoPagamento);
        
        logger.info("Pagamento confermato per ordine: {}", ordineId);
        return ordineRepository.save(ordine);
    }
    
    @Transactional
    public Ordine aggiornaStato(Long ordineId, StatoOrdine nuovoStato) {
        Ordine ordine = findById(ordineId);
        
        if (ordine.getStato() == StatoOrdine.ANNULLATO) {
            throw new RuntimeException("Non puoi modificare un ordine annullato");
        }
        
        if (ordine.getStato() == StatoOrdine.CONSEGNATO) {
            throw new RuntimeException("Non puoi modificare un ordine già consegnato");
        }
        
        ordine.setStato(nuovoStato);
        logger.info("Stato ordine {} aggiornato a: {}", ordineId, nuovoStato);
        return ordineRepository.save(ordine);
    }
    
    @Transactional
    public void annullaOrdine(Long ordineId) {
        Ordine ordine = findById(ordineId);
        
        if (ordine.getStato() == StatoOrdine.CONSEGNATO) {
            throw new RuntimeException("Non puoi annullare un ordine già consegnato");
        }
        
        if (ordine.getStato() == StatoOrdine.PAGATO) {
            throw new RuntimeException("Per annullare un ordine pagato, contatta l'assistenza");
        }
        
        for (RigaOrdine riga : ordine.getRighe()) {
            Prodotto prodotto = riga.getProdotto();
            prodotto.setQuantitaDisponibile(
                prodotto.getQuantitaDisponibile() + riga.getQuantita()
            );
            prodottoService.salvaProdotto(prodotto);
        }
        
        ordine.setStato(StatoOrdine.ANNULLATO);
        ordineRepository.save(ordine);
        logger.info("Ordine annullato: {}", ordineId);
    }
}