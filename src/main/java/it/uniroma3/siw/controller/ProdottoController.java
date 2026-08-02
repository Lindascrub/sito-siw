package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.CategoriaRepository;
import it.uniroma3.siw.repository.ProdottoRepository;
import it.uniroma3.siw.service.RecensioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class ProdottoController {

    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private RecensioneService recensioneService;  // ← AGGIUNGI QUESTO!

    @GetMapping("/prodotti")
    public String listaProdotti(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Prodotto> prodottiPage = prodottoRepository.findAll(pageable);
        
        model.addAttribute("prodotti", prodottiPage.getContent());
        model.addAttribute("prodottiPage", prodottiPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prodottiPage.getTotalPages());
        model.addAttribute("categorie", categoriaRepository.findAll());
        
        return "prodotti/list";
    }

    @GetMapping("/prodotto/{id}")
    public String dettaglioProdotto(@PathVariable Long id, Model model) {
        Prodotto prodotto = prodottoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        
        // Recupera le recensioni del prodotto
        List<Recensione> recensioni = recensioneService.getRecensioniByProdotto(id);
        
        model.addAttribute("prodotto", prodotto);
        model.addAttribute("recensioni", recensioni != null ? recensioni : Collections.emptyList());
        
        return "prodotti/dettaglio";
    }
    
    @GetMapping("/prodotti/categoria/{categoriaId}")
    public String prodottiPerCategoria(@PathVariable Long categoriaId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "8") int size,
                                       Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Prodotto> prodottiPage = prodottoRepository.findByCategoriaId(categoriaId, pageable);
        
        model.addAttribute("prodotti", prodottiPage.getContent());
        model.addAttribute("prodottiPage", prodottiPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prodottiPage.getTotalPages());
        model.addAttribute("categorie", categoriaRepository.findAll());
        model.addAttribute("categoriaSelezionata", categoriaId);
        
        return "prodotti/list";
    }
}