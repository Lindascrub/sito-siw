package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.CategoriaService;
import it.uniroma3.siw.service.TagliaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/prodotti")
public class ProdottoController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProdottoController.class);
    
    private final ProdottoService prodottoService;
    private final CategoriaService categoriaService;
    private final TagliaService tagliaService;
    
    public ProdottoController(ProdottoService prodottoService,
                              CategoriaService categoriaService,
                              TagliaService tagliaService) {
        this.prodottoService = prodottoService;
        this.categoriaService = categoriaService;
        this.tagliaService = tagliaService;
    }
    

    @GetMapping
    public String listProdotti(@RequestParam(required = false) String categoria,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) Double prezzoMin,
                               @RequestParam(required = false) Double prezzoMax,
                               Model model) {
        List<Prodotto> prodotti;
        List<Prodotto> filtrati;
        
        if (search != null && !search.isEmpty()) {
            prodotti = prodottoService.cercaPerNome(search);
            model.addAttribute("searchTerm", search);
        } else if (categoria != null && !categoria.isEmpty()) {
            // Cerca categoria per nome
            Categoria cat = categoriaService.findByNome(categoria);
            prodotti = prodottoService.cercaPerCategoria(cat.getId());
            model.addAttribute("categoriaSelezionata", categoria);
        } else {
            prodotti = prodottoService.findAllAttivi();
        }
        
        
        if (prezzoMin != null || prezzoMax != null) {
            Double min = prezzoMin != null ? prezzoMin : 0.0;
            Double max = prezzoMax != null ? prezzoMax : Double.MAX_VALUE;
            filtrati = prodotti.stream()
                .filter(p -> p.getPrezzo() >= min && p.getPrezzo() <= max)
                .collect(Collectors.toList());
        } else {
            filtrati = prodotti;
        }
        model.addAttribute("prodotti", filtrati);
        model.addAttribute("categorie", categoriaService.findAll());
        model.addAttribute("prezzoMin", prezzoMin);
        model.addAttribute("prezzoMax", prezzoMax);
        
        return "prodotti/list";
    }
    

    
    @GetMapping("/{id}")
    public String showProdotto(@PathVariable Long id, Model model) {
        Prodotto prodotto = prodottoService.findById(id);
        model.addAttribute("prodotto", prodotto);
        return "prodotti/show";
    }
    
    // Il vecchio backoffice viveva sotto /prodotti/admin: ora è tutto in /admin
    @GetMapping("/admin")
    public String vecchioBackoffice() {
        return "redirect:/admin/prodotti";
    }
    


}