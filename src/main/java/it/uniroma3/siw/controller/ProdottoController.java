package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.model.Taglia;
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
                               Model model) {
        
        List<Prodotto> prodotti;
        
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
        
        model.addAttribute("prodotti", prodotti);
        model.addAttribute("categorie", categoriaService.findAll());
        
        return "prodotti/list";
    }
    

    
    @GetMapping("/{id}")
    public String showProdotto(@PathVariable Long id, Model model) {
        Prodotto prodotto = prodottoService.findById(id);
        model.addAttribute("prodotto", prodotto);
        return "prodotti/show";
    }
    

    @GetMapping("/admin/new")
    public String createProdottoForm(Model model) {
        model.addAttribute("prodotto", new Prodotto());
        model.addAttribute("categorie", categoriaService.findAll());
        model.addAttribute("taglie", tagliaService.findAllOrdinate());
        return "prodotti/admin/form";
    }
    

    
    @PostMapping("/admin/new")
    public String saveProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto,
                               BindingResult bindingResult,
                               Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorie", categoriaService.findAll());
            model.addAttribute("taglie", tagliaService.findAllOrdinate());
            return "prodotti/admin/form";
        }
        
        try {
            prodottoService.salvaProdotto(prodotto);
            return "redirect:/prodotti/" + prodotto.getId();
        } catch (RuntimeException e) {
            bindingResult.rejectValue("codiceModello", "error.duplicate", e.getMessage());
            model.addAttribute("categorie", categoriaService.findAll());
            model.addAttribute("taglie", tagliaService.findAllOrdinate());
            return "prodotti/admin/form";
        }
    }

    
    @GetMapping("/admin/edit/{id}")
    public String editProdottoForm(@PathVariable Long id, Model model) {
        Prodotto prodotto = prodottoService.findById(id);
        model.addAttribute("prodotto", prodotto);
        model.addAttribute("categorie", categoriaService.findAll());
        model.addAttribute("taglie", tagliaService.findAllOrdinate());
        return "prodotti/admin/form";
    }
    
    @PostMapping("/admin/edit/{id}")
    public String updateProdotto(@PathVariable Long id,
                                 @Valid @ModelAttribute("prodotto") Prodotto prodotto,
                                 BindingResult bindingResult,
                                 Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorie", categoriaService.findAll());
            model.addAttribute("taglie", tagliaService.findAllOrdinate());
            return "prodotti/admin/form";
        }
        
        try {
            prodotto.setId(id);
            prodottoService.salvaProdotto(prodotto);
            return "redirect:/prodotti/" + id;
        } catch (RuntimeException e) {
            bindingResult.rejectValue("codiceModello", "error.duplicate", e.getMessage());
            model.addAttribute("categorie", categoriaService.findAll());
            model.addAttribute("taglie", tagliaService.findAllOrdinate());
            return "prodotti/admin/form";
        }
    }
    
    
    @GetMapping("/admin/disable/{id}")
    public String disableProdotto(@PathVariable Long id) {
        prodottoService.disattivaProdotto(id);
        return "redirect:/prodotti";
    }
    
    @GetMapping("/admin/enable/{id}")
    public String enableProdotto(@PathVariable Long id) {
        prodottoService.attivaProdotto(id);
        return "redirect:/prodotti";
    }
}