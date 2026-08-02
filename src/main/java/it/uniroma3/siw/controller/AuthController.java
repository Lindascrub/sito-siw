package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;  // ← AGGIUNGI QUESTO IMPORT!

@Controller
public class AuthController {

    @Autowired
    private CredenzialiService credenzialiService;
    
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }
    
    @GetMapping("/logout-manuale")
    public String logoutManuale(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(@RequestParam String nome,
                           @RequestParam String cognome,
                           @RequestParam String email,
                           @RequestParam String username,
                           @RequestParam String password,
                           Model model,
                           RedirectAttributes redirectAttributes) {  // ← AGGIUNGI QUESTO PARAMETRO!
        
        // Controlla se username esiste già
        if (credenzialiService.getCredenziali(username) != null) {
            redirectAttributes.addFlashAttribute("errore", "❌ Username già in uso!");
            return "redirect:/register";  // ← USO redirect invece di return "register"
        }

        // Crea l'utente
        Utente utente = new Utente();
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        
        // Crea le credenziali
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(username);
        credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
        
        // 🔥 CODIFICA LA PASSWORD PRIMA DI SALVARE!
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordCodificata = encoder.encode(password);
        credenziali.setPassword(passwordCodificata);
        
        System.out.println("🔐 Password originale: " + password);
        System.out.println("🔐 Password codificata: " + passwordCodificata);
        
        // Collega
        credenziali.setUtente(utente);
        utente.setCredenziali(credenziali);
        
        // Salva
        utenteService.saveUtente(utente);
        credenzialiService.saveCredenzialiRaw(credenziali);
        
        redirectAttributes.addFlashAttribute("successo", "✅ Registrazione completata! Ora puoi accedere.");
        return "redirect:/login";
    }
    @GetMapping("/oauth2/success")
    public String oauth2Success(OAuth2AuthenticationToken authentication, RedirectAttributes redirectAttributes) {
        // Prendi i dati dall'utente Google
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        String email = (String) attributes.get("email");
        String nome = (String) attributes.get("given_name");
        String cognome = (String) attributes.get("family_name");
        
        // Controlla se l'utente esiste già nel database
        Credenziali credenziali = credenzialiService.getCredenziali(email);
        
        if (credenziali == null) {
            // Crea un nuovo utente
            Utente utente = new Utente();
            utente.setNome(nome != null ? nome : "Google");
            utente.setCognome(cognome != null ? cognome : "User");
            utente.setEmail(email);
            
            Credenziali newCredenziali = new Credenziali();
            newCredenziali.setUsername(email);
            newCredenziali.setPassword(""); // Password vuota per OAuth
            newCredenziali.setRuolo(Credenziali.DEFAULT_ROLE);
            newCredenziali.setUtente(utente);
            utente.setCredenziali(newCredenziali);
            
            utenteService.saveUtente(utente);
            credenzialiService.saveCredenzialiRaw(newCredenziali);
            
            redirectAttributes.addFlashAttribute("successo", "✅ Benvenuto " + nome + "! Login con Google effettuato!");
        } else {
            redirectAttributes.addFlashAttribute("successo", "👋 Bentornato " + nome + "!");
        }
        
        return "redirect:/";
    }
}