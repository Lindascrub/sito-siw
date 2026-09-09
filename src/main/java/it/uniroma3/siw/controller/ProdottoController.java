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

import java.util.Comparator;
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
                               @RequestParam(required = false) String ordina,
                               @RequestParam(required = false) Integer page,
                               Model model) {
        List<Prodotto> prodotti;
        List<Prodotto> filtrati;

        boolean haSearch = search != null && !search.isEmpty();
        boolean haCategoria = categoria != null && !categoria.isEmpty();

        if (haSearch || haCategoria) {
            Long categoriaId = null;
            if (haCategoria) {
                Categoria cat = categoriaService.findByNome(categoria);
                categoriaId = cat.getId();
                model.addAttribute("categoriaSelezionata", categoria);
            }
            prodotti = prodottoService.cercaAvanzata(haSearch ? search : null, categoriaId);
            if (haSearch) {
                model.addAttribute("searchTerm", search);
            }
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

        List<Prodotto> ordinati = new java.util.ArrayList<>(filtrati);
        if ("prezzo-asc".equals(ordina)) {
            ordinati.sort(Comparator.comparing(Prodotto::getPrezzo));
        } else if ("prezzo-desc".equals(ordina)) {
            ordinati.sort(Comparator.comparing(Prodotto::getPrezzo).reversed());
        } else if ("nuovi".equals(ordina)) {
            ordinati.sort(Comparator.comparing(Prodotto::getId).reversed());
        }
        // "rilevanza" (o nessun valore): mantiene l'ordine restituito dalla ricerca/filtro

        int dimensionaPagina = 20;
        int totaleProdotti = ordinati.size();
        int totalePagine = (int) Math.ceil(totaleProdotti / (double) dimensionaPagina);
        int paginaCorrente = (page == null || page < 1) ? 1 : page;
        if (totalePagine > 0 && paginaCorrente > totalePagine) {
            paginaCorrente = totalePagine;
        }
        int daIndice = Math.min((paginaCorrente - 1) * dimensionaPagina, totaleProdotti);
        int aIndice = Math.min(daIndice + dimensionaPagina, totaleProdotti);
        List<Prodotto> prodottiPagina = ordinati.subList(daIndice, aIndice);

        model.addAttribute("prodotti", prodottiPagina);
        model.addAttribute("categorie", categoriaService.findAll());
        model.addAttribute("prezzoMin", prezzoMin);
        model.addAttribute("prezzoMax", prezzoMax);
        model.addAttribute("ordina", ordina);
        model.addAttribute("paginaCorrente", paginaCorrente);
        model.addAttribute("totalePagine", totalePagine);
        model.addAttribute("totaleProdotti", totaleProdotti);

        return "prodotti/list";
    }
    

    
    @GetMapping("/{id}")
    public String showProdotto(@PathVariable Long id, Model model) {
        Prodotto prodotto = prodottoService.findById(id);
        model.addAttribute("prodotto", prodotto);
        return "prodotti/show";
    }


}