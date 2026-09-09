package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.OrdineDto;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.StatoOrdine;
import it.uniroma3.siw.service.OrdineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** API REST di gestione ordini per l'amministratore. Protetta con hasRole(ADMIN). */
@RestController
@RequestMapping("/api/admin/ordini")
public class AdminOrdineRestController {

    public record StatoRequest(String stato) {
    }

    private final OrdineService ordineService;

    public AdminOrdineRestController(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    @GetMapping
    public List<OrdineDto> elenco(@RequestParam(required = false) StatoOrdine stato) {
        List<Ordine> ordini = (stato == null) ? ordineService.findAll() : ordineService.trovaPerStato(stato);
        return ordini.stream().map(OrdineDto::new).toList();
    }

    @GetMapping("/{id}")
    public OrdineDto dettaglio(@PathVariable Long id) {
        return new OrdineDto(ordineService.findById(id));
    }

    @PatchMapping("/{id}/stato")
    public ResponseEntity<?> aggiornaStato(@PathVariable Long id, @RequestBody StatoRequest richiesta) {
        try {
            StatoOrdine nuovoStato = StatoOrdine.valueOf(richiesta.stato());
            Ordine ordine = ordineService.aggiornaStato(id, nuovoStato);
            return ResponseEntity.ok(new OrdineDto(ordine));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }

    @PostMapping("/{id}/annulla")
    public ResponseEntity<?> annulla(@PathVariable Long id) {
        try {
            ordineService.annullaOrdine(id, true);
            return ResponseEntity.ok(new OrdineDto(ordineService.findById(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }
}
