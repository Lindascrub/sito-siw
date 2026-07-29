package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.CategoriaRepository;
import it.uniroma3.siw.repository.ProdottoRepository;
import it.uniroma3.siw.repository.TagliaRepository;

@Controller
public class ProdottoController {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private TagliaRepository tagliaRepository;
    
    // Mostra tutti i prodotti (catalogo)
    @GetMapping("/prodotti")
    public String listaProdotti(Model model) {
        List<Prodotto> prodotti = (List<Prodotto>) prodottoRepository.findAll();
        model.addAttribute("prodotti", prodotti);
        model.addAttribute("categorie", categoriaRepository.findAll());
        return "prodotti/list";
    }
    
    // Mostra il dettaglio di un prodotto
    @GetMapping("/prodotto/{id}")
    public String dettaglioProdotto(@PathVariable Long id, Model model) {
        Prodotto prodotto = prodottoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));
        model.addAttribute("prodotto", prodotto);
        return "prodotti/dettaglio";
    }
    
    // Filtra prodotti per categoria
    @GetMapping("/prodotti/categoria/{categoriaId}")
    public String prodottiPerCategoria(@PathVariable Long categoriaId, Model model) {
        List<Prodotto> prodotti = prodottoRepository.findByCategoriaId(categoriaId);
        model.addAttribute("prodotti", prodotti);
        model.addAttribute("categorie", categoriaRepository.findAll());
        return "prodotti/list";
    }
}