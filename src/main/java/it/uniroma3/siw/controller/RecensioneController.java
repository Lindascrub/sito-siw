package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.RecensioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;
    
    @Autowired
    private AuthenticationHelper authenticationHelper;

    @PostMapping("/recensione/aggiungi")
    public String aggiungiRecensione(@RequestParam Long prodottoId,
                                     @RequestParam String titolo,
                                     @RequestParam String testo,
                                     @RequestParam int valutazione,
                                     RedirectAttributes redirectAttributes) {
        
        Utente utente = authenticationHelper.getCurrentUser();
        
        if (utente == null) {
            redirectAttributes.addFlashAttribute("errore", "❌ Devi essere loggato per lasciare una recensione!");
            return "redirect:/login";
        }
        
        try {
            Recensione recensione = new Recensione();
            recensione.setTitolo(titolo);
            recensione.setTesto(testo);
            recensione.setValutazione(valutazione);
            // recensione.setProdotto(prodotto); // devi passare il prodotto
            // recensione.setUtente(utente);
            
            recensioneService.saveRecensione(recensione);
            redirectAttributes.addFlashAttribute("successo", "✅ Recensione aggiunta con successo!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errore", "❌ Errore: " + e.getMessage());
        }
        
        return "redirect:/prodotto/" + prodottoId;
    }
}