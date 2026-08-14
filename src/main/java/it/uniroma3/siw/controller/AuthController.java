package it.uniroma3.siw.controller;

import it.uniroma3.siw.dto.RegistrationDto;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UtenteService utenteService;
    
    public AuthController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }
    

    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, 
                        @RequestParam(required = false) String successo,
                        Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {
            return "redirect:/profilo";
        }
        if (error != null) {
            model.addAttribute("errore", "Email o password non validi");
        }
        if (successo != null) {
            model.addAttribute("successo", successo);
        }
        return "auth/login";
    }
    

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "auth/register";
    }

    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationDto") RegistrationDto registrationDto,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            model.addAttribute("errore", "Le password non coincidono");
            return "auth/register";
        }
        
        try {
            utenteService.registraUtente(
                registrationDto.getNome(),
                registrationDto.getCognome(),
                registrationDto.getEmail(),
                registrationDto.getUsername(),
                registrationDto.getPassword()
            );
            
            logger.info("Nuovo utente registrato: {}", registrationDto.getEmail());
            
            
            redirectAttributes.addAttribute("successo", "Registrazione completata! Ora puoi accedere.");
            return "redirect:/login";
            
        } catch (RuntimeException e) {
            model.addAttribute("errore", e.getMessage());
            return "auth/register";
        }
    }
}