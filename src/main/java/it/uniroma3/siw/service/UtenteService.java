package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import it.uniroma3.siw.repository.CredenzialiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UtenteService {
    
    private static final Logger logger = LoggerFactory.getLogger(UtenteService.class);
    private final UtenteRepository utenteRepository;
    private final CredenzialiRepository credenzialiRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UtenteService(UtenteRepository utenteRepository,
                         CredenzialiRepository credenzialiRepository,
                         PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.credenzialiRepository = credenzialiRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // =============================================
    // 🔹 METODI ESISTENTI
    // =============================================
    
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
    
    // ✅ AGGIUNGI QUESTO METODO!
    @Transactional(readOnly = true)
    public Utente getUtenteByUsername(String username) {
        logger.debug("Ricerca utente per username: {}", username);
        Credenziali credenziali = credenzialiRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Credenziali non trovate per: " + username));
        return credenziali.getUtente();
    }
    
    // ✅ AGGIUNGI QUESTO METODO!
    @Transactional
    public Utente salvaUtente(Utente utente) {
        logger.info("Salvataggio utente: {}", utente.getEmail());
        return utenteRepository.save(utente);
    }
    
    // =============================================
    // 🔹 REGISTRAZIONE NORMALE
    // =============================================
    
    @Transactional
    public Utente registraUtente(String nome, String cognome, String email, 
                                 String username, String password) {
        
        if (utenteRepository.existsByEmail(email)) {
            throw new RuntimeException("Email già registrata: " + email);
        }
        if (credenzialiRepository.existsByUsername(username)) {
            throw new RuntimeException("Username già utilizzato: " + username);
        }
        
        Utente utente = new Utente(nome, cognome, email);
        
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(username);
        credenziali.setPassword(passwordEncoder.encode(password));
        credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
        credenziali.setUtente(utente);
        
        utente.setCredenziali(credenziali);
        
        logger.info("Nuovo utente registrato: {}", email);
        return utenteRepository.save(utente);
    }
    
    // ✅ AGGIUNGI QUESTO METODO PER OAuth2!
    @Transactional
    public Utente registraUtenteOAuth2(String email, String nome, String cognome) {
        // Verifica se esiste già per email
        if (utenteRepository.existsByEmail(email)) {
            return utenteRepository.findByEmail(email).get();
        }
        
        Utente utente = new Utente(nome, cognome, email);
        
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(email);  // Usa email come username
        credenziali.setPassword(passwordEncoder.encode("oauth2_" + System.currentTimeMillis()));
        credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
        credenziali.setUtente(utente);
        
        utente.setCredenziali(credenziali);
        
        logger.info("Nuovo utente registrato via OAuth2: {}", email);
        return utenteRepository.save(utente);
    }
}