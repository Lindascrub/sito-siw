package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.StatoOrdine;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.service.PaypalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint REST usati dai PayPal Smart Payment Buttons nella pagina di
 * checkout/dettaglio ordine per creare e catturare il pagamento.
 */
@RestController
@RequestMapping("/ordini/{ordineId}/paypal")
public class PaypalController {

    private final PaypalService paypalService;
    private final OrdineService ordineService;
    private final AuthenticationHelper authenticationHelper;

    public PaypalController(PaypalService paypalService,
                             OrdineService ordineService,
                             AuthenticationHelper authenticationHelper) {
        this.paypalService = paypalService;
        this.ordineService = ordineService;
        this.authenticationHelper = authenticationHelper;
    }

    public record CatturaRequest(String paypalOrderId) {
    }

    @PostMapping("/crea")
    public ResponseEntity<?> creaOrdinePaypal(@PathVariable Long ordineId) {
        Ordine ordine = ordineService.findById(ordineId);
        controllaProprietario(ordine);

        if (ordine.getStato() != StatoOrdine.CREATO) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("errore", "L'ordine non è in attesa di pagamento"));
        }

        String paypalOrderId = paypalService.creaOrdinePaypal(ordine.getTotale());
        return ResponseEntity.ok(Map.of("id", paypalOrderId));
    }

    @PostMapping("/cattura")
    public ResponseEntity<?> catturaOrdinePaypal(@PathVariable Long ordineId,
                                                  @RequestBody CatturaRequest richiesta) {
        Ordine ordine = ordineService.findById(ordineId);
        controllaProprietario(ordine);

        if (ordine.getStato() != StatoOrdine.CREATO) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("errore", "L'ordine non è in attesa di pagamento"));
        }

        boolean completato = paypalService.catturaOrdinePaypal(richiesta.paypalOrderId());
        if (!completato) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("errore", "Il pagamento PayPal non è andato a buon fine"));
        }

        Ordine pagato = ordineService.confermaPagamento(ordineId, "PAYPAL");
        return ResponseEntity.ok(Map.of("stato", pagato.getStato().name()));
    }

    /** Un cliente può pagare solo i propri ordini. */
    private void controllaProprietario(Ordine ordine) {
        Utente utente = authenticationHelper.getCurrentUser();
        if (utente == null || ordine.getUtente() == null
                || !ordine.getUtente().getId().equals(utente.getId())) {
            throw new AccessDeniedException("Questo ordine non ti appartiene");
        }
    }
}
