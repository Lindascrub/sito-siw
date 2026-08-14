package it.uniroma3.siw.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.RigaCarrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.ProdottoRepository;
import it.uniroma3.siw.repository.RigaCarrelloRepository;
import it.uniroma3.siw.repository.RigaOrdineRepository;
import it.uniroma3.siw.repository.UtenteRepository;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;


@Service
public class ProdottoService {
    
    private ProdottoRepository prodottoRepository;
    private final RigaOrdineRepository rigaOrdineRepository;
    private final RigaCarrelloRepository rigaCarrelloRepository;
    private final UtenteRepository utenteRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProdottoService.class);
    
    public ProdottoService(ProdottoRepository prodottoRepository,
                           RigaOrdineRepository rigaOrdineRepository,
                           RigaCarrelloRepository rigaCarrelloRepository,
                           UtenteRepository utenteRepository) {
        this.prodottoRepository = prodottoRepository;
        this.rigaOrdineRepository = rigaOrdineRepository;
        this.rigaCarrelloRepository = rigaCarrelloRepository;
        this.utenteRepository = utenteRepository;
    }

   @Transactional(readOnly = true)
    public List<Prodotto> findAll() {
        return prodottoRepository.findAll().stream()
            .sorted(Comparator.comparing(Prodotto::getId))
            .toList();
    }

    
    @Transactional
    public Prodotto salvaDaForm(Prodotto datiForm, Categoria categoria,
                                String taglieCsv, String coloriCsv) {

        Prodotto prodotto = (datiForm.getId() == null)
            ? new Prodotto()
            : findById(datiForm.getId());

        prodotto.setNome(datiForm.getNome());
        prodotto.setDescrizione(datiForm.getDescrizione());
        prodotto.setPrezzo(datiForm.getPrezzo());
        prodotto.setQuantitaDisponibile(datiForm.getQuantitaDisponibile());
        prodotto.setUrlImage(vuotoComeNull(datiForm.getUrlImage()));
        prodotto.setCodiceModello(vuotoComeNull(datiForm.getCodiceModello()));
        prodotto.setAttivo(datiForm.getAttivo() == null ? Boolean.TRUE : datiForm.getAttivo());
        prodotto.setCategoria(categoria);
        prodotto.setTaglieDisponibili(daCsv(taglieCsv));
        prodotto.setColoriDisponibili(daCsv(coloriCsv));

        return salvaProdotto(prodotto);
    }

    
    @Transactional
    public void eliminaProdotto(Long id) {
        Prodotto prodotto = findById(id);

        if (rigaOrdineRepository.existsByProdottoId(id)) {
            throw new RuntimeException("Il prodotto \"" + prodotto.getNome()
                + "\" è presente in ordini già effettuati: puoi solo disattivarlo.");
        }

        // Ripulisce i riferimenti nei carrelli e nei preferiti
        List<RigaCarrello> righe = rigaCarrelloRepository.findByProdottoId(id);
        if (!righe.isEmpty()) {
            rigaCarrelloRepository.deleteAll(righe);
        }
        for (Utente utente : utenteRepository.findAll()) {
            if (utente.getPreferiti().remove(prodotto)) {
                utenteRepository.save(utente);
            }
        }

        prodottoRepository.delete(prodotto);
        logger.info("Prodotto eliminato: {}", prodotto.getNome());
    }

    private String vuotoComeNull(String valore) {
        return (valore == null || valore.isBlank()) ? null : valore.trim();
    }

    private List<String> daCsv(String csv) {
        List<String> valori = new ArrayList<>();
        if (csv == null || csv.isBlank()) {
            return valori;
        }
        Arrays.stream(csv.split(","))
            .map(String::trim)
            .filter(v -> !v.isEmpty())
            .forEach(v -> {
                if (!valori.contains(v)) {
                    valori.add(v);
                }
            });
        return valori;
    }

   
    @Transactional(readOnly = true)
    public List<Prodotto> findBestSeller(int limite) {
        logger.debug("Calcolo best seller in base alle vendite reali");
        List<Object[]> venduti = rigaOrdineRepository.findProdottiPiuVenduti();

        List<Prodotto> risultato = new ArrayList<>();
        Set<Long> idAggiunti = new LinkedHashSet<>();
        for (Object[] riga : venduti) {
            Long prodottoId = (Long) riga[0];
            prodottoRepository.findById(prodottoId)
                .filter(p -> Boolean.TRUE.equals(p.getAttivo()))
                .ifPresent(p -> {
                    if (idAggiunti.add(p.getId())) {
                        risultato.add(p);
                    }
                });
            if (risultato.size() >= limite) {
                break;
            }
        }

        // Se non ci sono ancora abbastanza vendite, completa con i prodotti disponibili
        if (risultato.size() < limite) {
            for (Prodotto p : findAllDisponibili()) {
                if (idAggiunti.add(p.getId())) {
                    risultato.add(p);
                }
                if (risultato.size() >= limite) {
                    break;
                }
            }
        }
        return risultato;
    }

    
    @Transactional(readOnly = true)
    public List<Prodotto> findUltimiArrivi(int limite) {
        logger.debug("Recupero ultimi arrivi");
        return findAllAttivi().stream()
            .sorted(Comparator.comparing(Prodotto::getId).reversed())
            .limit(limite)
            .toList();
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