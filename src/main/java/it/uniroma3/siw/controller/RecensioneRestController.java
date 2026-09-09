package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.RecensioneDto;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.RecensioneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * API REST sulle recensioni di un prodotto, usata dal frontend React.
 * La GET è pubblica, la POST richiede un utente autenticato (vedi SecurityConfig).
 */
@RestController
@RequestMapping("/api/prodotti/{prodottoId}/recensioni")
public class RecensioneRestController {

    public record NuovaRecensioneRequest(String titolo, String testo, int valutazione) {
    }

    private final RecensioneService recensioneService;
    private final AuthenticationHelper authenticationHelper;

    public RecensioneRestController(RecensioneService recensioneService,
                                     AuthenticationHelper authenticationHelper) {
        this.recensioneService = recensioneService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<RecensioneDto> elenco(@PathVariable Long prodottoId) {
        return recensioneService.trovaPerProdotto(prodottoId).stream().map(RecensioneDto::new).toList();
    }

    @PostMapping
    public ResponseEntity<?> aggiungi(@PathVariable Long prodottoId,
                                       @RequestBody NuovaRecensioneRequest richiesta) {
        Utente utente = authenticationHelper.getCurrentUser();
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("errore", "Devi accedere per lasciare una recensione"));
        }

        try {
            Recensione salvata = recensioneService.salvaRecensione(
                    utente.getId(), prodottoId,
                    richiesta.titolo(), richiesta.testo(), richiesta.valutazione());
            return ResponseEntity.status(HttpStatus.CREATED).body(new RecensioneDto(salvata));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }
}
