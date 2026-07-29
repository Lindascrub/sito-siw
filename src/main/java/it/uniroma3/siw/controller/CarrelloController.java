package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.CarrelloService;
import it.uniroma3.siw.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrello")
public class CarrelloController {
    
    @Autowired
    private CarrelloService carrelloService;
    
    @Autowired
    private OrdineService ordineService;
    
    @Autowired
    private AuthenticationHelper authenticationHelper;  // ← Iniettato qui

    @GetMapping
    public String carrello(Model model) {
        Utente utente = authenticationHelper.getCurrentUser();  // ← OK!
        Carrello carrello = carrelloService.getCarrello(utente);
        model.addAttribute("carrello", carrello);
        return "carrello/view";
    }
   
    @PostMapping("/aggiungi/{prodottoId}")
    public String aggiungiAlCarrello(@PathVariable Long prodottoId, @RequestParam(defaultValue = "1") int quantita) {
        Utente utente = authenticationHelper.getCurrentUser();  // ← CORRETTO! (NON static)
        carrelloService.aggiungiProdotto(utente, prodottoId, quantita);
        return "redirect:/carrello";
    }
    
    @GetMapping("/rimuovi/{prodottoId}")
    public String rimuoviDalCarrello(@PathVariable Long prodottoId) {
        Utente utente = authenticationHelper.getCurrentUser();  // ← CORRETTO! (NON static)
        carrelloService.rimuoviProdotto(utente, prodottoId);
        return "redirect:/carrello";
    }
    
    @PostMapping("/checkout")
    public String checkout() {
        Utente utente = authenticationHelper.getCurrentUser();  // ← CORRETTO! (NON static)
        Carrello carrello = carrelloService.getCarrello(utente);
        
        if (carrello.getRighe().isEmpty()) {
            return "redirect:/carrello?empty=true";
        }
        
        ordineService.creaOrdine(utente, carrello);
        return "redirect:/ordini?success=true";
    }
}