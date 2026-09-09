package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.ProdottoDto;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.ProdottoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** API REST di sola lettura sul catalogo prodotti, usata dal frontend React. */
@RestController
@RequestMapping("/api/prodotti")
public class ProdottoRestController {

    private final ProdottoService prodottoService;

    public ProdottoRestController(ProdottoService prodottoService) {
        this.prodottoService = prodottoService;
    }

    @GetMapping
    public List<ProdottoDto> elenco(@RequestParam(required = false) String search,
                                     @RequestParam(required = false) Long categoriaId) {
        List<Prodotto> prodotti = (search == null && categoriaId == null)
                ? prodottoService.findAllAttivi()
                : prodottoService.cercaAvanzata(search, categoriaId);
        return prodotti.stream().map(ProdottoDto::new).toList();
    }

    @GetMapping("/{id}")
    public ProdottoDto dettaglio(@PathVariable Long id) {
        return new ProdottoDto(prodottoService.findById(id));
    }
}
