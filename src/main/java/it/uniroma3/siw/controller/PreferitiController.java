package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/preferiti")
public class PreferitiController {

    private final UtenteService utenteService;
    private final ProdottoService prodottoService;
    private final AuthenticationHelper authenticationHelper;

    public PreferitiController(UtenteService utenteService,
                                ProdottoService prodottoService,
                                AuthenticationHelper authenticationHelper) {
        this.utenteService = utenteService;
        this.prodottoService = prodottoService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String lista(Model model) {
        Utente utente = authenticationHelper.getCurrentUser();
        model.addAttribute("preferiti", utente.getPreferiti());
        return "preferiti/list";
    }

    @PostMapping("/toggle/{prodottoId}")
    public String toggle(@PathVariable Long prodottoId, @RequestParam(required = false) String redirect) {
        Utente utente = authenticationHelper.getCurrentUser();
        Prodotto prodotto = prodottoService.findById(prodottoId);

        if (utente.getPreferiti().contains(prodotto)) {
            utente.getPreferiti().remove(prodotto);
        } else {
            utente.getPreferiti().add(prodotto);
        }
        utenteService.salvaUtente(utente);

        if (redirect != null && !redirect.isBlank()) {
            return "redirect:" + redirect;
        }
        return "redirect:/preferiti";
    }
}
