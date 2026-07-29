package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private CredenzialiService credenzialiService;
    
    public Utente saveUtente(Utente utente) {
        return utenteRepository.save(utente);
    }
    
    public Utente getUtenteByUsername(String username) {
        Credenziali credenziali = credenzialiService.getCredenziali(username);
        if (credenziali != null) {
            return credenziali.getUtente();
        }
        return null;
    }
    
    public Utente getUtente(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }
    
    public Iterable<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }
}