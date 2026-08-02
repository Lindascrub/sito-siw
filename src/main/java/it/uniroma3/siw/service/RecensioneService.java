package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecensioneService {
    
    @Autowired
    private RecensioneRepository recensioneRepository;
    
    public List<Recensione> getRecensioniByProdotto(Long prodottoId) {
        return recensioneRepository.findByProdottoIdOrderByDataCreazioneDesc(prodottoId);
    }
    
    public Recensione saveRecensione(Recensione recensione) {
        return recensioneRepository.save(recensione);
    }
}