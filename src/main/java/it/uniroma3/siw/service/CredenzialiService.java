package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
    
    @Autowired
    private CredenzialiRepository credenzialiRepository;
    
    public Credenziali getCredenziali(String username) {
        return credenzialiRepository.findByUsername(username).orElse(null);
    }
    
    public Credenziali saveCredenziali(Credenziali credenziali) {
        // Cripta la password prima di salvare
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        credenziali.setPassword(encoder.encode(credenziali.getPassword()));
        return credenzialiRepository.save(credenziali);
    }
}