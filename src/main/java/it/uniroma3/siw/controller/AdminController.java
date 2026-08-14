package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Categoria;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.StatoOrdine;
import it.uniroma3.siw.service.CategoriaService;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.TagliaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final OrdineService ordineService;
    private final CategoriaService categoriaService;
    private final ProdottoService prodottoService;
    private final TagliaService tagliaService;

    public AdminController(OrdineService ordineService,
                           CategoriaService categoriaService,
                           ProdottoService prodottoService,
                           TagliaService tagliaService) {
        this.ordineService = ordineService;
        this.categoriaService = categoriaService;
        this.prodottoService = prodottoService;
        this.tagliaService = tagliaService;
    }

   
    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        List<Prodotto> prodotti = prodottoService.findAll();
        model.addAttribute("numeroProdotti", prodotti.size());
        model.addAttribute("numeroProdottiAttivi",
                prodotti.stream().filter(p -> Boolean.TRUE.equals(p.getAttivo())).count());
        model.addAttribute("numeroCategorie", categoriaService.findAll().size());
        model.addAttribute("ordiniRecent", ordineService.trovaPerStato(StatoOrdine.CREATO));
        return "admin/dashboard";
    }

  
    @GetMapping("/prodotti")
    public String prodotti(@RequestParam(required = false) String search,
                           @RequestParam(required = false) String stato,
                           Model model) {
        List<Prodotto> prodotti = prodottoService.findAll();

        if (search != null && !search.isBlank()) {
            String q = search.toLowerCase();
            prodotti = prodotti.stream()
                    .filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(q))
                    .toList();
        }
        if ("attivi".equals(stato)) {
            prodotti = prodotti.stream().filter(p -> Boolean.TRUE.equals(p.getAttivo())).toList();
        } else if ("disattivati".equals(stato)) {
            prodotti = prodotti.stream().filter(p -> !Boolean.TRUE.equals(p.getAttivo())).toList();
        }

        model.addAttribute("prodotti", prodotti);
        model.addAttribute("searchTerm", search);
        model.addAttribute("statoSelezionato", stato);
        return "admin/prodotti/list";
    }

    @GetMapping("/prodotti/new")
    public String nuovoProdotto(Model model) {
        model.addAttribute("prodotto", new Prodotto());
        preparaFormProdotto(model, null, null);
        return "admin/prodotti/form";
    }

    // Vecchio link della dashboard, mantenuto per compatibilità
    @GetMapping("/prodotti/form")
    public String nuovoProdottoAlias() {
        return "redirect:/admin/prodotti/new";
    }

    @GetMapping("/prodotti/{id}/edit")
    public String modificaProdotto(@PathVariable Long id, Model model) {
        Prodotto prodotto = prodottoService.findById(id);
        model.addAttribute("prodotto", prodotto);
        preparaFormProdotto(model,
                String.join(", ", prodotto.getTaglieDisponibili()),
                String.join(", ", prodotto.getColoriDisponibili()));
        return "admin/prodotti/form";
    }

    @PostMapping("/prodotti/salva")
    public String salvaProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto,
                                BindingResult bindingResult,
                                @RequestParam(required = false) Long categoriaId,
                                @RequestParam(required = false) String nuovaCategoria,
                                @RequestParam(required = false) String nuovaCategoriaDescrizione,
                                @RequestParam(required = false) String taglieCsv,
                                @RequestParam(required = false) String coloriCsv,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        Categoria categoria = null;

        
        if (nuovaCategoria != null && !nuovaCategoria.isBlank()) {
            try {
                Categoria daCreare = new Categoria();
                daCreare.setNome(nuovaCategoria.trim());
                daCreare.setDescrizione(
                        (nuovaCategoriaDescrizione == null || nuovaCategoriaDescrizione.isBlank())
                                ? nuovaCategoria.trim()
                                : nuovaCategoriaDescrizione.trim());
                categoria = categoriaService.salvaCategoria(daCreare);
            } catch (RuntimeException e) {
            	categoria = categoriaService.findByNome(nuovaCategoria.trim());
            }
        } else if (categoriaId != null) {
            categoria = categoriaService.findById(categoriaId);
        }

        if (categoria == null) {
            bindingResult.reject("categoria.mancante", "Seleziona una categoria oppure creane una nuova");
        }

        if (bindingResult.hasErrors()) {
            preparaFormProdotto(model, taglieCsv, coloriCsv);
            return "admin/prodotti/form";
        }

        try {
            Prodotto salvato = prodottoService.salvaDaForm(prodotto, categoria, taglieCsv, coloriCsv);
            redirectAttributes.addFlashAttribute("successo",
                    "Prodotto \"" + salvato.getNome() + "\" salvato correttamente.");
            return "redirect:/admin/prodotti";
        } catch (RuntimeException e) {
            logger.error("Errore salvataggio prodotto: {}", e.getMessage());
            bindingResult.reject("errore.salvataggio", e.getMessage());
            preparaFormProdotto(model, taglieCsv, coloriCsv);
            return "admin/prodotti/form";
        }
    }

    @PostMapping("/prodotti/{id}/stock")
    public String aggiornaStock(@PathVariable Long id,
                                @RequestParam Integer quantita,
                                RedirectAttributes redirectAttributes) {
        try {
            prodottoService.aggiornaQuantita(id, quantita);
            redirectAttributes.addFlashAttribute("successo", "Quantità aggiornata.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
        }
        return "redirect:/admin/prodotti";
    }

    @PostMapping("/prodotti/{id}/disattiva")
    public String disattiva(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        prodottoService.disattivaProdotto(id);
        redirectAttributes.addFlashAttribute("successo",
                "Prodotto disattivato: non è più in vendita, ma resta in archivio.");
        return "redirect:/admin/prodotti";
    }

    @PostMapping("/prodotti/{id}/attiva")
    public String attiva(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        prodottoService.attivaProdotto(id);
        redirectAttributes.addFlashAttribute("successo", "Prodotto riattivato e di nuovo in vendita.");
        return "redirect:/admin/prodotti";
    }

    @PostMapping("/prodotti/{id}/elimina")
    public String elimina(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            prodottoService.eliminaProdotto(id);
            redirectAttributes.addFlashAttribute("successo", "Prodotto eliminato definitivamente.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
        }
        return "redirect:/admin/prodotti";
    }

    private void preparaFormProdotto(Model model, String taglieCsv, String coloriCsv) {
        model.addAttribute("categorie", categoriaService.findAll());
        model.addAttribute("taglie", tagliaService.findAllOrdinate());
        model.addAttribute("taglieCsv", taglieCsv);
        model.addAttribute("coloriCsv", coloriCsv);
    }

   
    @GetMapping("/categorie")
    public String categorie(Model model) {
        model.addAttribute("categorie", categoriaService.findAll());
        return "admin/categorie/list";
    }

    @GetMapping("/categorie/new")
    public String nuovaCategoriaForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/categorie/form";
    }

    @GetMapping("/categorie/{id}/edit")
    public String modificaCategoriaForm(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.findById(id));
        return "admin/categorie/form";
    }

    @PostMapping("/categorie/salva")
    public String salvaCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/categorie/form";
        }
        try {
            categoriaService.salvaCategoria(categoria);
            redirectAttributes.addFlashAttribute("successo", "Categoria salvata.");
            return "redirect:/admin/categorie";
        } catch (RuntimeException e) {
            bindingResult.rejectValue("nome", "error.duplicate", e.getMessage());
            return "admin/categorie/form";
        }
    }

    @PostMapping("/categorie/{id}/elimina")
    public String eliminaCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.eliminaCategoria(id);
            redirectAttributes.addFlashAttribute("successo", "Categoria eliminata.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errore",
                    "Impossibile eliminare la categoria: ci sono ancora prodotti collegati.");
        }
        return "redirect:/admin/categorie";
    }

   
    @GetMapping("/ordini")
    public String ordini(@RequestParam(required = false) StatoOrdine stato, Model model) {
        List<Ordine> ordini = (stato == null)
                ? ordineService.findAll()
                : ordineService.trovaPerStato(stato);
        model.addAttribute("ordini", ordini);
        model.addAttribute("statoSelezionato", stato);
        model.addAttribute("stati", StatoOrdine.values());
        return "admin/ordini/list";
    }

    @GetMapping("/ordini/{id}")
    public String dettaglioOrdine(@PathVariable Long id, Model model) {
        model.addAttribute("ordine", ordineService.findById(id));
        model.addAttribute("stati", StatoOrdine.values());
        return "admin/ordini/dettaglio";
    }

    @PostMapping("/ordini/{id}/stato")
    public String aggiornaStato(@PathVariable Long id,
                                @RequestParam StatoOrdine stato,
                                RedirectAttributes redirectAttributes) {
        try {
            ordineService.aggiornaStato(id, stato);
            redirectAttributes.addFlashAttribute("successo", "Stato dell'ordine aggiornato a " + stato + ".");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
        }
        return "redirect:/admin/ordini/" + id;
    }

    @PostMapping("/ordini/{id}/annulla")
    public String annullaOrdine(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            ordineService.annullaOrdine(id, true);
            redirectAttributes.addFlashAttribute("successo",
                    "Ordine annullato: la merce è tornata in magazzino.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
        }
        return "redirect:/admin/ordini/" + id;
    }
}
