package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredenzialiService {
    
    @Autowired
    private CredenzialiRepository credenzialiRepository;
    
    public Credenziali getCredenziali(String username) {
        return credenzialiRepository.findByUsername(username).orElse(null);
    }
    
    // METODO PER SALVARE CON CODIFICA
    public Credenziali saveCredenziali(Credenziali credenziali) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        credenziali.setPassword(encoder.encode(credenziali.getPassword()));
        return credenzialiRepository.save(credenziali);
    }
    
    // METODO PER SALVARE SENZA CODIFICA (solo per admin già codificata)
    public Credenziali saveCredenzialiRaw(Credenziali credenziali) {
        return credenzialiRepository.save(credenziali);
    }
}