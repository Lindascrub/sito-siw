package it.uniroma3.siw.controller;

import it.uniroma3.siw.service.ProdottoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    private final ProdottoService prodottoService;
    
    public HomeController(ProdottoService prodottoService) {
        this.prodottoService = prodottoService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        // Prodotti in evidenza (i primi 6 disponibili)
        model.addAttribute("prodottiInEvidenza", 
            prodottoService.findAllDisponibili().stream().limit(6).toList());
        // Best seller reali, calcolati sulle vendite effettive (non più fissi)
        model.addAttribute("bestSeller", prodottoService.findBestSeller(4));
        // Ultimi prodotti aggiunti al catalogo, con la loro immagine reale
        model.addAttribute("ultimiArrivi", prodottoService.findUltimiArrivi(4));
        return "index";
    }
}