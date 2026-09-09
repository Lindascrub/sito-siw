package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.ArticoloCarrelloDto;
import it.uniroma3.siw.dto.OrdineDto;
import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.RigaCarrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.CarrelloService;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * API REST per creare e consultare un ordine dal carrello dell'utente corrente,
 * usata dal checkout del frontend React. Rispecchia la logica già presente in
 * OrdineController (versione Thymeleaf), qui esposta come JSON.
 */
@RestController
@RequestMapping("/api/ordini")
public class OrdineRestController {

    public record NuovoOrdineRequest(String indirizzo, String citta, String cap) {
    }

    private final OrdineService ordineService;
    private final CarrelloService carrelloService;
    private final UtenteService utenteService;
    private final AuthenticationHelper authenticationHelper;

    public OrdineRestController(OrdineService ordineService,
                                 CarrelloService carrelloService,
                                 UtenteService utenteService,
                                 AuthenticationHelper authenticationHelper) {
        this.ordineService = ordineService;
        this.carrelloService = carrelloService;
        this.utenteService = utenteService;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping
    public ResponseEntity<?> creaOrdine(@RequestBody NuovoOrdineRequest richiesta) {
        Utente utente = authenticationHelper.getCurrentUser();
        Carrello carrello = carrelloService.trovaPerUtente(utente.getId());

        if (carrello.getRighe().isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", "La borsa è vuota"));
        }

        List<ArticoloCarrelloDto> articoli = new ArrayList<>();
        for (RigaCarrello riga : carrello.getRighe()) {
            articoli.add(new ArticoloCarrelloDto(riga.getProdotto().getId(), riga.getQuantita(), riga.getTaglia(), riga.getColore()));
        }

        try {
            Ordine ordine = ordineService.creaOrdine(utente.getId(), articoli, richiesta.indirizzo(), richiesta.citta(), richiesta.cap());
            carrelloService.svuotaCarrello(utente.getId());

            utente.setIndirizzo(richiesta.indirizzo());
            utente.setCitta(richiesta.citta());
            utente.setCap(richiesta.cap());
            utenteService.salvaUtente(utente);

            return ResponseEntity.status(HttpStatus.CREATED).body(new OrdineDto(ordine));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public OrdineDto dettaglio(@PathVariable Long id) {
        Ordine ordine = ordineService.findById(id);
        Utente utente = authenticationHelper.getCurrentUser();
        if (utente == null || ordine.getUtente() == null || !ordine.getUtente().getId().equals(utente.getId())) {
            throw new AccessDeniedException("Questo ordine non ti appartiene");
        }
        return new OrdineDto(ordine);
    }
}
