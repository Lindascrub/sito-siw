package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.CarrelloService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    private final AuthenticationHelper authenticationHelper;
    private final CarrelloService carrelloService;

    public GlobalController(AuthenticationHelper authenticationHelper,
                            CarrelloService carrelloService) {
        this.authenticationHelper = authenticationHelper;
        this.carrelloService = carrelloService;
    }

    @ModelAttribute("utenteCorrente")
    public Utente getUtenteCorrente() {
        return authenticationHelper.getCurrentUser();
    }

    /** Serve alla navbar per mostrare il numero di articoli nella borsa. */
    @ModelAttribute("carrello")
    public Carrello getCarrelloCorrente() {
        Utente utente = authenticationHelper.getCurrentUser();
        if (utente == null) {
            return null;
        }
        try {
            return carrelloService.trovaPerUtente(utente.getId());
        } catch (RuntimeException e) {
            return null;
        }
    }
}
