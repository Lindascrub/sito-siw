package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordini")
public class OrdineController {
    
    @Autowired
    private OrdineService ordineService;
    
    @Autowired
    private AuthenticationHelper authenticationHelper;  // ← Iniettato qui

    @GetMapping
    public String listaOrdini(Model model) {
        Utente utente = authenticationHelper.getCurrentUser();  // ← NON static!
        List<Ordine> ordini = ordineService.getOrdiniUtente(utente);
        model.addAttribute("ordini", ordini);
        return "ordini/list";
    }
    
    @GetMapping("/{id}")
    public String dettaglioOrdine(@PathVariable Long id, Model model) {
        Utente utente = authenticationHelper.getCurrentUser();  // ← NON static!
        Ordine ordine = ordineService.getOrdine(id);
        
        if (!ordine.getUtente().getId().equals(utente.getId())) {
            return "redirect:/ordini?error=true";
        }
        
        model.addAttribute("ordine", ordine);
        return "ordini/dettaglio";
    }
}