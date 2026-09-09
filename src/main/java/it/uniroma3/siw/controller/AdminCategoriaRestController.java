package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.CategoriaDto;
import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** API REST di gestione categorie per l'amministratore. Protetta con hasRole(ADMIN). */
@RestController
@RequestMapping("/api/admin/categorie")
public class AdminCategoriaRestController {

    public record CategoriaRequest(String nome, String descrizione) {
    }

    private final CategoriaService categoriaService;

    public AdminCategoriaRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDto> elenco() {
        return categoriaService.findAll().stream().map(CategoriaDto::new).toList();
    }

    @PostMapping
    public ResponseEntity<?> crea(@RequestBody CategoriaRequest richiesta) {
        return salva(null, richiesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifica(@PathVariable Long id, @RequestBody CategoriaRequest richiesta) {
        return salva(id, richiesta);
    }

    private ResponseEntity<?> salva(Long id, CategoriaRequest richiesta) {
        try {
            Categoria categoria = (id != null) ? categoriaService.findById(id) : new Categoria();
            categoria.setNome(richiesta.nome());
            categoria.setDescrizione(richiesta.descrizione());
            Categoria salvata = categoriaService.salvaCategoria(categoria);
            return ResponseEntity.status(id == null ? HttpStatus.CREATED : HttpStatus.OK).body(new CategoriaDto(salvata));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> elimina(@PathVariable Long id) {
        try {
            categoriaService.eliminaCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("errore", "Impossibile eliminare la categoria: ci sono ancora prodotti collegati."));
        }
    }
}
