package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private CredenzialiService credenzialiService;
    
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("utente", new Utente());
        model.addAttribute("credenziali", new Credenziali());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("utente") Utente utente,
                           @Valid @ModelAttribute("credenziali") Credenziali credenziali,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // Controlla se username esiste già
        if (credenzialiService.getCredenziali(credenziali.getUsername()) != null) {
            model.addAttribute("error", "Username già in uso");
            return "register";
        }

        // Imposta il ruolo di default
        credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
        
        // Collega utente e credenziali
        credenziali.setUtente(utente);
        utente.setCredenziali(credenziali);
        
        // Salva
        utenteService.saveUtente(utente);
        credenzialiService.saveCredenziali(credenziali);
        
        return "registration-success";
    }
}

