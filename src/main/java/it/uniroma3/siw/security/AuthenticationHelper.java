package it.uniroma3.siw.security;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.UtenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationHelper.class);
    private final UtenteService utenteService;
    
    public AuthenticationHelper(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    public Utente getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.debug("Nessuna autenticazione attiva");
            return null;
        }

        Object principal = authentication.getPrincipal();
        
        // Caso 1: Utente normale (login con username/password)
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            logger.debug("Utente autenticato con username: {}", username);
            return utenteService.getUtenteByUsername(username);
        }
        
        // Caso 2: Utente OAuth2 (login con Google)
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String email = (String) oauthToken.getPrincipal().getAttributes().get("email");
            logger.debug("Utente autenticato via OAuth2: {}", email);
            return utenteService.getUtenteByUsername(email);
        }

        logger.warn("Tipo di autenticazione non riconosciuto: {}", authentication.getClass().getName());
        return null;
    }
}