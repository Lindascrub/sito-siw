package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private AuthenticationHelper authenticationHelper;

    @ModelAttribute("utenteCorrente")
    public Utente getUtenteCorrente() {
        Utente utente = authenticationHelper.getCurrentUser();
        System.out.println("🔍 Utente corrente: " + (utente != null ? utente.getNome() : "null"));
        return utente;
    }
    
}