package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.service.CategoriaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorie")
public class CategoriaController {
    
    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);
    private final CategoriaService categoriaService;
    
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    
    // =============================================
    // 🔹 LISTA CATEGORIE (per ADMIN)
    // =============================================
    
    @GetMapping("/admin")
    public String listCategorie(Model model) {
        model.addAttribute("categorie", categoriaService.findAll());
        return "categorie/admin/list";
    }
    
    // =============================================
    // 🔹 FORM NUOVA CATEGORIA
    // =============================================
    
    @GetMapping("/admin/new")
    public String createCategoriaForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorie/admin/form";
    }
    
    @PostMapping("/admin/new")
    public String saveCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
                                BindingResult bindingResult,
                                Model model) {
        
        if (bindingResult.hasErrors()) {
            return "categorie/admin/form";
        }
        
        try {
            categoriaService.salvaCategoria(categoria);
            return "redirect:/categorie/admin";
        } catch (RuntimeException e) {
            bindingResult.rejectValue("nome", "error.duplicate", e.getMessage());
            return "categorie/admin/form";
        }
    }
    
    // =============================================
    // 🔹 MODIFICA CATEGORIA
    // =============================================
    
    @GetMapping("/admin/edit/{id}")
    public String editCategoriaForm(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.findById(id);
        model.addAttribute("categoria", categoria);
        return "categorie/admin/form";
    }
    
    @PostMapping("/admin/edit/{id}")
    public String updateCategoria(@PathVariable Long id,
                                  @Valid @ModelAttribute("categoria") Categoria categoria,
                                  BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return "categorie/admin/form";
        }
        
        try {
            categoria.setId(id);
            categoriaService.salvaCategoria(categoria);
            return "redirect:/categorie/admin";
        } catch (RuntimeException e) {
            bindingResult.rejectValue("nome", "error.duplicate", e.getMessage());
            return "categorie/admin/form";
        }
    }
    
    // =============================================
    // 🔹 ELIMINA CATEGORIA
    // =============================================
    
    @GetMapping("/admin/delete/{id}")
    public String deleteCategoria(@PathVariable Long id) {
        categoriaService.eliminaCategoria(id);
        return "redirect:/categorie/admin";
    }
}