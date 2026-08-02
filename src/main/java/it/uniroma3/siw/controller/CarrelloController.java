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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrello")
public class CarrelloController {
    
    @Autowired
    private CarrelloService carrelloService;
    
    @Autowired
    private OrdineService ordineService;
    
    @Autowired
    private AuthenticationHelper authenticationHelper;

    @GetMapping
    public String carrello(Model model) {
        Utente utente = authenticationHelper.getCurrentUser();
        Carrello carrello = carrelloService.getCarrello(utente);
        model.addAttribute("carrello", carrello);
        return "carrello/view";
    }
    @PostMapping("/aggiungi/{prodottoId}")
    public String aggiungiAlCarrello(@PathVariable Long prodottoId, 
                                     @RequestParam(defaultValue = "1") int quantita,
                                     RedirectAttributes redirectAttributes) {
        Utente utente = authenticationHelper.getCurrentUser();
        
        try {
            carrelloService.aggiungiProdotto(utente, prodottoId, quantita);
            redirectAttributes.addFlashAttribute("successo", "✅ Prodotto aggiunto al carrello!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errore", "❌ Errore: " + e.getMessage());
        }
        
        return "redirect:/carrello";
    }
    
    @GetMapping("/rimuovi/{prodottoId}")
    public String rimuoviDalCarrello(@PathVariable Long prodottoId, RedirectAttributes redirectAttributes) {
        Utente utente = authenticationHelper.getCurrentUser();
        carrelloService.rimuoviProdotto(utente, prodottoId);
        redirectAttributes.addFlashAttribute("successo", "🗑️ Prodotto rimosso dal carrello!");
        return "redirect:/carrello";
    }
    
    @PostMapping("/checkout")
    public String checkout(RedirectAttributes redirectAttributes) {
        Utente utente = authenticationHelper.getCurrentUser();
        Carrello carrello = carrelloService.getCarrello(utente);
        
        if (carrello.getRighe().isEmpty()) {
            redirectAttributes.addFlashAttribute("errore", "❌ Il carrello è vuoto!");
            return "redirect:/carrello";
        }
        
        try {
            ordineService.creaOrdine(utente, carrello);
            redirectAttributes.addFlashAttribute("successo", "🎉 Ordine creato con successo!");
            return "redirect:/ordini";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errore", "❌ Errore: " + e.getMessage());
            return "redirect:/carrello";
        }
    }
    @PostMapping("/aggiorna/{prodottoId}")
    public String aggiornaQuantita(@PathVariable Long prodottoId, 
                                   @RequestParam int quantita,
                                   RedirectAttributes redirectAttributes) {
        Utente utente = authenticationHelper.getCurrentUser();
        
        if (quantita <= 0) {
            carrelloService.rimuoviProdotto(utente, prodottoId);
            redirectAttributes.addFlashAttribute("successo", "🗑️ Prodotto rimosso dal carrello!");
        } else {
            carrelloService.aggiornaQuantita(utente, prodottoId, quantita);
            redirectAttributes.addFlashAttribute("successo", "✅ Quantità aggiornata!");
        }
        
        return "redirect:/carrello";
    }
    
}