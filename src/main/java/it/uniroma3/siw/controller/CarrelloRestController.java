package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.CarrelloDto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.CarrelloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * API REST sul carrello dell'utente corrente, usata dal frontend React.
 * Tutte le operazioni richiedono un utente autenticato (vedi SecurityConfig:
 * non è tra i path pubblici, quindi ricade sotto anyRequest().authenticated()).
 */
@RestController
@RequestMapping("/api/carrello")
public class CarrelloRestController {

    public record RigaRequest(Integer quantita, String taglia, String colore) {
    }

    private final CarrelloService carrelloService;
    private final AuthenticationHelper authenticationHelper;

    public CarrelloRestController(CarrelloService carrelloService, AuthenticationHelper authenticationHelper) {
        this.carrelloService = carrelloService;
        this.authenticationHelper = authenticationHelper;
    }

    private Utente utenteCorrente() {
        return authenticationHelper.getCurrentUser();
    }

    @GetMapping
    public ResponseEntity<?> visualizza() {
        Utente utente = utenteCorrente();
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errore", "Devi accedere per vedere il carrello"));
        }
        return ResponseEntity.ok(new CarrelloDto(carrelloService.trovaPerUtente(utente.getId())));
    }

    @PostMapping("/{prodottoId}")
    public ResponseEntity<?> aggiungi(@PathVariable Long prodottoId, @RequestBody(required = false) RigaRequest richiesta) {
        Utente utente = utenteCorrente();
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errore", "Devi accedere per aggiungere articoli al carrello"));
        }
        int quantita = (richiesta != null && richiesta.quantita() != null) ? richiesta.quantita() : 1;
        String taglia = richiesta != null ? richiesta.taglia() : null;
        String colore = richiesta != null ? richiesta.colore() : null;
        try {
            var carrello = carrelloService.aggiungiProdotto(utente.getId(), prodottoId, quantita, taglia, colore);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CarrelloDto(carrello));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }

    @PutMapping("/{prodottoId}")
    public ResponseEntity<?> aggiornaQuantita(@PathVariable Long prodottoId, @RequestBody RigaRequest richiesta) {
        Utente utente = utenteCorrente();
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errore", "Devi accedere per modificare il carrello"));
        }
        try {
            var carrello = carrelloService.aggiornaRiga(utente.getId(), prodottoId, richiesta.quantita(), richiesta.taglia(), richiesta.colore());
            return ResponseEntity.ok(new CarrelloDto(carrello));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }

    @DeleteMapping("/{prodottoId}")
    public ResponseEntity<?> rimuovi(@PathVariable Long prodottoId) {
        Utente utente = utenteCorrente();
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errore", "Devi accedere per modificare il carrello"));
        }
        return ResponseEntity.ok(new CarrelloDto(carrelloService.rimuoviProdotto(utente.getId(), prodottoId)));
    }

    @DeleteMapping
    public ResponseEntity<?> svuota() {
        Utente utente = utenteCorrente();
        if (utente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errore", "Devi accedere per modificare il carrello"));
        }
        carrelloService.svuotaCarrello(utente.getId());
        return ResponseEntity.noContent().build();
    }
}
