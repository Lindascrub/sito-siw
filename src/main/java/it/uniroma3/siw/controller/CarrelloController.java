package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.CarrelloService;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.UtenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrello")
public class CarrelloController {
    
    private static final Logger logger = LoggerFactory.getLogger(CarrelloController.class);
    
    private final CarrelloService carrelloService;
    private final ProdottoService prodottoService;
    private final UtenteService utenteService;
    private final AuthenticationHelper authenticationHelper;
    
    public CarrelloController(CarrelloService carrelloService,
                              ProdottoService prodottoService,
                              UtenteService utenteService,
                              AuthenticationHelper authenticationHelper) {
        this.carrelloService = carrelloService;
        this.prodottoService = prodottoService;
        this.utenteService = utenteService;
        this.authenticationHelper = authenticationHelper;
    }
    
  
    private Utente getCurrentUser() {
        return authenticationHelper.getCurrentUser();
    }
    
    
    @GetMapping
    public String viewCart(Model model) {
        Utente utente = getCurrentUser();
        Carrello carrello = carrelloService.trovaPerUtente(utente.getId());
        
        model.addAttribute("carrello", carrello);
        model.addAttribute("totale", carrello.getTotale());
        model.addAttribute("numeroArticoli", carrello.getNumeroArticoli());
        
        return "carrello/view";
    }
    
  
    @PostMapping("/add/{prodottoId}")
    public String addToCart(@PathVariable Long prodottoId,
                            @RequestParam(defaultValue = "1") Integer quantita,
                            @RequestParam(required = false) String taglia,
                            @RequestParam(required = false) String colore,
                            RedirectAttributes redirectAttributes) {
        
        Utente utente = getCurrentUser();
        
        try {
            carrelloService.aggiungiProdotto(utente.getId(), prodottoId, quantita, taglia, colore);
            logger.info("Prodotto {} aggiunto al carrello", prodottoId);
        } catch (RuntimeException e) {
            logger.error("Errore aggiunta al carrello: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
        }
        
        return "redirect:/carrello";
    }
    
    
    @PostMapping("/update-riga/{prodottoId}")
    public String updateRiga(@PathVariable Long prodottoId,
                             @RequestParam(required = false) Integer quantita,
                             @RequestParam(required = false) String taglia,
                             @RequestParam(required = false) String colore,
                             RedirectAttributes redirectAttributes) {
        Utente utente = getCurrentUser();
        try {
            carrelloService.aggiornaRiga(utente.getId(), prodottoId, quantita, taglia, colore);
        } catch (RuntimeException e) {
            logger.error("Errore aggiornamento riga carrello: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
        }
        return "redirect:/carrello";
    }
    
    @PostMapping("/update/{prodottoId}")
    public String updateQuantity(@PathVariable Long prodottoId,
                                 @RequestParam Integer quantita) {
        Utente utente = getCurrentUser();
        try {
            carrelloService.aggiornaQuantita(utente.getId(), prodottoId, quantita);
        } catch (RuntimeException e) {
            logger.error("Errore aggiornamento quantità: {}", e.getMessage());
        }
        return "redirect:/carrello";
    }
    
    @PostMapping("/update-details/{prodottoId}")
    public String updateDetails(@PathVariable Long prodottoId,
                                @RequestParam String taglia,
                                @RequestParam String colore) {
        Utente utente = getCurrentUser();
        carrelloService.aggiornaDettagli(utente.getId(), prodottoId, taglia, colore);
        return "redirect:/carrello";
    }
    
   
    @PostMapping("/remove/{prodottoId}")
    public String removeFromCart(@PathVariable Long prodottoId) {
        Utente utente = getCurrentUser();
        carrelloService.rimuoviProdotto(utente.getId(), prodottoId);
        return "redirect:/carrello";
    }
    
  
    @PostMapping("/clear")
    public String clearCart() {
        Utente utente = getCurrentUser();
        carrelloService.svuotaCarrello(utente.getId());
        return "redirect:/carrello";
    }


    @GetMapping("/checkout")
    public String redirectToCheckout() {
        return "redirect:/ordini/checkout";
    }
}