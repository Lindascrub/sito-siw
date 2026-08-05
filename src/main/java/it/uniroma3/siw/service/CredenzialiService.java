package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CredenzialiService {
    
    private static final Logger logger = LoggerFactory.getLogger(CredenzialiService.class);
    private final CredenzialiRepository credenzialiRepository;
    
    public CredenzialiService(CredenzialiRepository credenzialiRepository) {
        this.credenzialiRepository = credenzialiRepository;
    }
    
    @Transactional(readOnly = true)
    public Credenziali findByUsername(String username) {
        return credenzialiRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Credenziali non trovate per: " + username));
    }
    
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return credenzialiRepository.existsByUsername(username);
    }
    
    @Transactional
    public Credenziali salva(Credenziali credenziali) {
        return credenzialiRepository.save(credenziali);
    }
}