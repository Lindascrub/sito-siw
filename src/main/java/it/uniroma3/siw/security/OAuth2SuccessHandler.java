package it.uniroma3.siw.security;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(OAuth2SuccessHandler.class);
    private final UtenteService utenteService;
    
    public OAuth2SuccessHandler(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                       HttpServletResponse response,
                                       Authentication authentication) throws IOException {
        
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();
        
        String email = (String) attributes.get("email");
        String nome = (String) attributes.get("given_name");
        String cognome = (String) attributes.get("family_name");
        
        // Se nome o cognome sono null, usa valori di default
        if (nome == null) nome = "Utente";
        if (cognome == null) cognome = "OAuth2";
        
        // Registra o recupera l'utente
        Utente utente = utenteService.registraUtenteOAuth2(email, nome, cognome);
        logger.info("Login OAuth2 per: {}", utente.getEmail());
        
        response.sendRedirect("/");
    }
}