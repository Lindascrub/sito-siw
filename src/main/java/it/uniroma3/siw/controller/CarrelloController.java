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
    
    // =============================================
    // 🔹 RECUPERA UTENTE CORRENTE
    // =============================================
    
    private Utente getCurrentUser() {
        return authenticationHelper.getCurrentUser();
    }
    
    // =============================================
    // 🔹 VISUALIZZA CARRELLO
    // =============================================
    
    @GetMapping
    public String viewCart(Model model) {
        Utente utente = getCurrentUser();
        Carrello carrello = carrelloService.trovaPerUtente(utente.getId());
        
        model.addAttribute("carrello", carrello);
        model.addAttribute("totale", carrello.getTotale());
        model.addAttribute("numeroArticoli", carrello.getNumeroArticoli());
        
        return "carrello/view";
    }
    
    // =============================================
    // 🔹 AGGIUNGI PRODOTTO AL CARRELLO
    // =============================================
    
    @PostMapping("/add/{prodottoId}")
    public String addToCart(@PathVariable Long prodottoId,
                            @RequestParam(defaultValue = "1") Integer quantita,
                            @RequestParam(required = false) String taglia,
                            @RequestParam(required = false) String colore) {
        
        Utente utente = getCurrentUser();
        
        try {
            carrelloService.aggiungiProdotto(utente.getId(), prodottoId, quantita);
            logger.info("Prodotto {} aggiunto al carrello", prodottoId);
        } catch (RuntimeException e) {
            logger.error("Errore aggiunta al carrello: {}", e.getMessage());
            // TODO: Aggiungere messaggio di errore alla sessione
        }
        
        return "redirect:/carrello";
    }
    
    // =============================================
    // 🔹 RIMUOVI PRODOTTO DAL CARRELLO
    // =============================================
    
    @PostMapping("/remove/{prodottoId}")
    public String removeFromCart(@PathVariable Long prodottoId) {
        Utente utente = getCurrentUser();
        carrelloService.rimuoviProdotto(utente.getId(), prodottoId);
        return "redirect:/carrello";
    }
    
    // =============================================
    // 🔹 SVUOTA CARRELLO
    // =============================================
    
    @PostMapping("/clear")
    public String clearCart() {
        Utente utente = getCurrentUser();
        carrelloService.svuotaCarrello(utente.getId());
        return "redirect:/carrello";
    }
}