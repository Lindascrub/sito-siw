package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.repository.CategoriaRepository;

@Controller
public class CategoriaController {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @GetMapping("/categorie")
    public String listaCategorie(Model model) {
        List<Categoria> categorie = (List<Categoria>) categoriaRepository.findAll();
        model.addAttribute("categorie", categorie);
        return "categorie/list";
    }
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
}