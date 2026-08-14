package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recensioni")
public class RecensioneController {

	private static final Logger logger = LoggerFactory.getLogger(RecensioneController.class);

	private final RecensioneService recensioneService;
	private final UtenteService utenteService;
	private final ProdottoService prodottoService;  
	private final AuthenticationHelper authenticationHelper;



	public RecensioneController(RecensioneService recensioneService,
			UtenteService utenteService,
			ProdottoService prodottoService,
			AuthenticationHelper authenticationHelper) {
		this.recensioneService = recensioneService;
		this.utenteService = utenteService;
		this.prodottoService = prodottoService;  // ← AGGIUNGI!
		this.authenticationHelper = authenticationHelper;
	}

	private Utente getCurrentUser() {
		return authenticationHelper.getCurrentUser();
	}


	@GetMapping("/new/{prodottoId}")
	public String createRecensioneForm(@PathVariable Long prodottoId, Model model) {
		Recensione recensione = new Recensione();
		recensione.setProdotto(prodottoService.findById(prodottoId));
		model.addAttribute("recensione", recensione);
		return "recensioni/form";
	}

	@PostMapping("/new/{prodottoId}")
	public String saveRecensione(@PathVariable Long prodottoId,
			@Valid @ModelAttribute("recensione") Recensione recensione,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "recensioni/form";
		}

		try {
			Utente utente = getCurrentUser();
			recensioneService.salvaRecensione(
					utente.getId(),
					prodottoId,
					recensione.getTitolo(),
					recensione.getTesto(),
					recensione.getValutazione()
					);
			return "redirect:/prodotti/" + prodottoId;
		} catch (RuntimeException e) {
			model.addAttribute("errore", e.getMessage());
			return "recensioni/form";
		}
	}
}