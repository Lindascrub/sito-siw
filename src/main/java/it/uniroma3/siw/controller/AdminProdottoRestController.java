package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.ProdottoDto;
import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.CategoriaService;
import it.uniroma3.siw.service.ProdottoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * API REST di gestione prodotti per l'amministratore, usata dal pannello admin
 * del frontend React. Protetta in SecurityConfig con hasRole(ADMIN).
 */
@RestController
@RequestMapping("/api/admin/prodotti")
public class AdminProdottoRestController {

    public record ProdottoRequest(
            String nome,
            String descrizione,
            Double prezzo,
            Integer quantitaDisponibile,
            String urlImage,
            String codiceModello,
            Boolean attivo,
            Long categoriaId,
            List<String> taglie,
            List<String> colori
    ) {
    }

    public record AttivoRequest(boolean attivo) {
    }

    private final ProdottoService prodottoService;
    private final CategoriaService categoriaService;

    public AdminProdottoRestController(ProdottoService prodottoService, CategoriaService categoriaService) {
        this.prodottoService = prodottoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<ProdottoDto> elenco() {
        return prodottoService.findAll().stream().map(ProdottoDto::new).toList();
    }

    @PostMapping
    public ResponseEntity<?> crea(@RequestBody ProdottoRequest richiesta) {
        return salva(null, richiesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifica(@PathVariable Long id, @RequestBody ProdottoRequest richiesta) {
        return salva(id, richiesta);
    }

    private ResponseEntity<?> salva(Long id, ProdottoRequest richiesta) {
        try {
            Categoria categoria = categoriaService.findById(richiesta.categoriaId());

            Prodotto datiForm = new Prodotto();
            datiForm.setId(id);
            datiForm.setNome(richiesta.nome());
            datiForm.setDescrizione(richiesta.descrizione());
            datiForm.setPrezzo(richiesta.prezzo());
            datiForm.setQuantitaDisponibile(richiesta.quantitaDisponibile());
            datiForm.setUrlImage(richiesta.urlImage());
            datiForm.setCodiceModello(richiesta.codiceModello());
            datiForm.setAttivo(richiesta.attivo());

            String taglieCsv = richiesta.taglie() != null ? String.join(",", richiesta.taglie()) : "";
            String coloriCsv = richiesta.colori() != null ? String.join(",", richiesta.colori()) : "";

            Prodotto salvato = prodottoService.salvaDaForm(datiForm, categoria, taglieCsv, coloriCsv);
            return ResponseEntity.status(id == null ? HttpStatus.CREATED : HttpStatus.OK).body(new ProdottoDto(salvato));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/attivo")
    public ResponseEntity<?> aggiornaAttivo(@PathVariable Long id, @RequestBody AttivoRequest richiesta) {
        if (richiesta.attivo()) {
            prodottoService.attivaProdotto(id);
        } else {
            prodottoService.disattivaProdotto(id);
        }
        return ResponseEntity.ok(new ProdottoDto(prodottoService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> elimina(@PathVariable Long id) {
        try {
            prodottoService.eliminaProdotto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errore", e.getMessage()));
        }
    }
}
