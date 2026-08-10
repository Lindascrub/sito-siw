package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profilo")
public class ProfiloController {

    private final AuthenticationHelper authenticationHelper;
    private final UtenteService utenteService;

    public ProfiloController(AuthenticationHelper authenticationHelper, UtenteService utenteService) {
        this.authenticationHelper = authenticationHelper;
        this.utenteService = utenteService;
    }

    @GetMapping
    public String profilo(Model model) {
        model.addAttribute("utente", authenticationHelper.getCurrentUser());
        return "auth/profilo";
    }

    @PostMapping
    public String aggiorna(@RequestParam String nome,
                            @RequestParam String cognome,
                            @RequestParam(required = false) String telefono,
                            @RequestParam(required = false) String indirizzo,
                            @RequestParam(required = false) String citta,
                            @RequestParam(required = false) String cap,
                            RedirectAttributes redirectAttributes) {
        Utente utente = authenticationHelper.getCurrentUser();
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setTelefono(telefono);
        utente.setIndirizzo(indirizzo);
        utente.setCitta(citta);
        utente.setCap(cap);
        utenteService.salvaUtente(utente);
        redirectAttributes.addFlashAttribute("successo", "Profilo aggiornato con successo!");
        return "redirect:/profilo";
    }
}
