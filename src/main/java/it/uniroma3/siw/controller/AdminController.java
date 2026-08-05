package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.StatoOrdine;
import it.uniroma3.siw.service.CategoriaService;
import it.uniroma3.siw.service.OrdineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    
    private final OrdineService ordineService;
    private final CategoriaService categoriaService;
    
    public AdminController(OrdineService ordineService,
                           CategoriaService categoriaService) {
        this.ordineService = ordineService;
        this.categoriaService = categoriaService;
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("ordiniRecent", ordineService.trovaPerStato(StatoOrdine.CREATO));
        model.addAttribute("categorie", categoriaService.findAll());
        return "admin/dashboard";
    }
    
    @GetMapping("/orders")
    public String gestisciOrdini(Model model) {
        model.addAttribute("ordini", ordineService.trovaPerStato(StatoOrdine.CREATO));
        model.addAttribute("stati", StatoOrdine.values());
        return "admin/orders";
    }
    
    @PostMapping("/orders/{id}/status")
    public String aggiornaStato(@PathVariable Long id,
                                @RequestParam StatoOrdine stato) {
        ordineService.aggiornaStato(id, stato);
        return "redirect:/admin/orders";
    }
}