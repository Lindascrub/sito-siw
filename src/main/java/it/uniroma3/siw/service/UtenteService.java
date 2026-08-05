package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UtenteService {
    
    private static final Logger logger = LoggerFactory.getLogger(UtenteService.class);
    private final UtenteRepository utenteRepository;
    private final CredenzialiService credenzialiService;
    private final PasswordEncoder passwordEncoder;
    
    public UtenteService(UtenteRepository utenteRepository,
                         CredenzialiService credenzialiService,
                         PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.credenzialiService = credenzialiService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional(readOnly = true)
    public Utente findById(Long id) {
        return utenteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utente non trovato: " + id));
    }
    
    @Transactional(readOnly = true)
    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Utente non trovato: " + email));
    }
    
    @Transactional(readOnly = true)
    public List<Utente> searchUtenti(String nome, String cognome) {
        return utenteRepository.searchUtenti(nome, cognome);
    }
    
    @Transactional
    public Utente registraUtente(String nome, String cognome, String email, 
                                 String username, String password) {
        
        // Verifica unicità email
        if (utenteRepository.existsByEmail(email)) {
            throw new RuntimeException("Email già registrata: " + email);
        }
        
        // Verifica unicità username
        if (credenzialiService.existsByUsername(username)) {
            throw new RuntimeException("Username già utilizzato: " + username);
        }
        
        // Creo l'utente
        Utente utente = new Utente(nome, cognome, email);
        
        // Creo le credenziali
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(username);
        credenziali.setPassword(passwordEncoder.encode(password));
        credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
        credenziali.setUtente(utente);
        
        utente.setCredenziali(credenziali);
        
        logger.info("Nuovo utente registrato: {}", email);
        return utenteRepository.save(utente);
    }
}