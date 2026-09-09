package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.security.AuthenticationHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint minimo per far sapere al frontend React chi è l'amministratore loggato,
 * usato per mostrare (o bloccare) il pannello admin. Protetto da hasRole(ADMIN)
 * come tutto ciò che sta sotto /api/admin/** (vedi SecurityConfig).
 */
@RestController
@RequestMapping("/api/admin/whoami")
public class AdminWhoAmIController {

    private final AuthenticationHelper authenticationHelper;

    public AdminWhoAmIController(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public Map<String, String> whoami() {
        Utente utente = authenticationHelper.getCurrentUser();
        return Map.of(
                "nome", utente.getNome(),
                "cognome", utente.getCognome(),
                "email", utente.getEmail()
        );
    }
}
