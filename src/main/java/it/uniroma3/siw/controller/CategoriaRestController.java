package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.service.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/** API REST di sola lettura sulle categorie, usata dal filtro del frontend React. */
@RestController
@RequestMapping("/api/categorie")
public class CategoriaRestController {

    private final CategoriaService categoriaService;

    public CategoriaRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Map<String, Object>> elenco() {
        List<Categoria> categorie = categoriaService.findAll();
        return categorie.stream()
                .map(c -> Map.<String, Object>of("id", c.getId(), "nome", c.getNome()))
                .toList();
    }
}
