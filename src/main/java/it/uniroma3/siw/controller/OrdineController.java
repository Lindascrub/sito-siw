package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.ArticoloCarrelloDto;
import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.RigaCarrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CarrelloService;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.service.UtenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ordini")
public class OrdineController {
    
    private static final Logger logger = LoggerFactory.getLogger(OrdineController.class);
    
    private final OrdineService ordineService;
    private final CarrelloService carrelloService;
    private final UtenteService utenteService;
    
    public OrdineController(OrdineService ordineService,
                            CarrelloService carrelloService,
                            UtenteService utenteService) {
        this.ordineService = ordineService;
        this.carrelloService = carrelloService;
        this.utenteService = utenteService;
    }
    
    private Utente getCurrentUser() {
        // Per ora, assumiamo utente 1L (poi con Spring Security)
        return utenteService.findById(1L);
    }
    
    // =============================================
    // 🔹 VISUALIZZA CHECKOUT
    // =============================================
    
    @GetMapping("/checkout")
    public String checkout(Model model) {
        Utente utente = getCurrentUser();
        Carrello carrello = carrelloService.trovaPerUtente(utente.getId());
        
        if (carrello.getRighe().isEmpty()) {
            return "redirect:/carrello";
        }
        
        model.addAttribute("carrello", carrello);
        model.addAttribute("utente", utente);
        return "ordini/checkout";
    }
    
    // =============================================
    // 🔹 CONFERMA ORDINE
    // =============================================
    
    @PostMapping("/checkout")
    public String confermaOrdine(@RequestParam String indirizzo,
                                 @RequestParam String citta,
                                 @RequestParam String cap,
                                 Model model) {
        
        Utente utente = getCurrentUser();
        Carrello carrello = carrelloService.trovaPerUtente(utente.getId());
        
        if (carrello.getRighe().isEmpty()) {
            return "redirect:/carrello";
        }
        
        // Converti RigaCarrello in ArticoloCarrelloDTO
        List<ArticoloCarrelloDto> articoli = new ArrayList<>();
        for (RigaCarrello riga : carrello.getRighe()) {
            ArticoloCarrelloDto dto = new ArticoloCarrelloDto();
            dto.setProdottoId(riga.getProdotto().getId());
            dto.setQuantita(riga.getQuantita());
            dto.setTaglia(riga.getTaglia());
            dto.setColore(riga.getColore());
            articoli.add(dto);
        }
        
        try {
            // Crea l'ordine
            Ordine ordine = ordineService.creaOrdine(
                utente.getId(), articoli, indirizzo, citta, cap
            );
            
            // Svuota il carrello
            carrelloService.svuotaCarrello(utente.getId());
            
            model.addAttribute("ordine", ordine);
            return "ordini/conferma";
            
        } catch (RuntimeException e) {
            model.addAttribute("errore", e.getMessage());
            model.addAttribute("carrello", carrello);
            model.addAttribute("utente", utente);
            return "ordini/checkout";
        }
    }
    
    // =============================================
    // 🔹 STORICO ORDINI
    // =============================================
    
    @GetMapping("/history")
    public String storicoOrdini(Model model) {
        Utente utente = getCurrentUser();
        List<Ordine> ordini = ordineService.trovaPerUtente(utente.getId());
        model.addAttribute("ordini", ordini);
        return "ordini/history";
    }
    
    @GetMapping("/{id}")
    public String dettaglioOrdine(@PathVariable Long id, Model model) {
        Ordine ordine = ordineService.findById(id);
        model.addAttribute("ordine", ordine);
        return "ordini/dettaglio";
    }
}