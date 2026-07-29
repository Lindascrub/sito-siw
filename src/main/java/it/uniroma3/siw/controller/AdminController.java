package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Taglia;
import it.uniroma3.siw.repository.CategoriaRepository;
import it.uniroma3.siw.repository.ProdottoRepository;
import it.uniroma3.siw.repository.TagliaRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private TagliaRepository tagliaRepository;

    // ============ DASHBOARD ============
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totaleProdotti", prodottoRepository.count());
        model.addAttribute("totaleCategorie", categoriaRepository.count());
        model.addAttribute("totaleTaglie", tagliaRepository.count());
        return "admin/dashboard";
    }

    // ============ GESTIONE PRODOTTI ============
    
    // Lista prodotti (area admin)
    @GetMapping("/prodotti")
    public String listaProdottiAdmin(Model model) {
        List<Prodotto> prodotti = (List<Prodotto>) prodottoRepository.findAll();
        model.addAttribute("prodotti", prodotti);
        return "admin/prodotti/list";
    }

    // Form per nuovo prodotto
    @GetMapping("/prodotti/nuovo")
    public String formNuovoProdotto(Model model) {
        model.addAttribute("prodotto", new Prodotto());
        model.addAttribute("categorie", categoriaRepository.findAll());
        model.addAttribute("taglie", tagliaRepository.findAll());
        return "admin/prodotti/form";
    }

    // Salva nuovo prodotto
    @PostMapping("/prodotti")
    public String salvaProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto,
                                 BindingResult bindingResult,
                                 @RequestParam(required = false) List<Long> taglieIds,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorie", categoriaRepository.findAll());
            model.addAttribute("taglie", tagliaRepository.findAll());
            return "admin/prodotti/form";
        }
        
        // Salva il prodotto
        Prodotto saved = prodottoRepository.save(prodotto);
        
        // Associa le taglie (se selezionate)
        if (taglieIds != null && !taglieIds.isEmpty()) {
            List<Taglia> taglie = (List<Taglia>) tagliaRepository.findAllById(taglieIds);
            saved.setTaglie(taglie);
            prodottoRepository.save(saved);
        }
        
        return "redirect:/admin/prodotti";
    }

    // Modifica prodotto
    @GetMapping("/prodotti/modifica/{id}")
    public String formModificaProdotto(@PathVariable Long id, Model model) {
        Prodotto prodotto = prodottoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        model.addAttribute("prodotto", prodotto);
        model.addAttribute("categorie", categoriaRepository.findAll());
        model.addAttribute("taglie", tagliaRepository.findAll());
        return "admin/prodotti/form";
    }

    // Elimina prodotto
    @GetMapping("/prodotti/elimina/{id}")
    public String eliminaProdotto(@PathVariable Long id) {
        prodottoRepository.deleteById(id);
        return "redirect:/admin/prodotti";
    }

    // ============ GESTIONE CATEGORIE ============
    
    @GetMapping("/categorie")
    public String listaCategorieAdmin(Model model) {
        model.addAttribute("categorie", categoriaRepository.findAll());
        return "admin/categorie/list";
    }

    @GetMapping("/categorie/nuovo")
    public String formNuovaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/categorie/form";
    }

    @PostMapping("/categorie")
    public String salvaCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categorie/form";
        }
        categoriaRepository.save(categoria);
        return "redirect:/admin/categorie";
    }

    @GetMapping("/categorie/elimina/{id}")
    public String eliminaCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/admin/categorie";
    }

    // ============ GESTIONE TAGLIE ============
    
    @GetMapping("/taglie")
    public String listaTaglieAdmin(Model model) {
        model.addAttribute("taglie", tagliaRepository.findAll());
        return "admin/taglie/list";
    }

    @GetMapping("/taglie/nuovo")
    public String formNuovaTaglia(Model model) {
        model.addAttribute("taglia", new Taglia());
        return "admin/taglie/form";
    }

    @PostMapping("/taglie")
    public String salvaTaglia(@Valid @ModelAttribute("taglia") Taglia taglia,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/taglie/form";
        }
        tagliaRepository.save(taglia);
        return "redirect:/admin/taglie";
    }

    @GetMapping("/taglie/elimina/{id}")
    public String eliminaTaglia(@PathVariable Long id) {
        tagliaRepository.deleteById(id);
        return "redirect:/admin/taglie";
    }
}