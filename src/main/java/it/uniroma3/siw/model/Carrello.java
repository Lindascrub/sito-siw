package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrello {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrello_id")
    private List<RigaCarrello> righe = new ArrayList<>();
    
    public Carrello() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    
    public List<RigaCarrello> getRighe() { return righe; }
    public void setRighe(List<RigaCarrello> righe) { this.righe = righe; }
    
    public double getTotale() {
        return righe.stream()
            .mapToDouble(r -> r.getQuantita() * r.getProdotto().getPrezzo())
            .sum();
    }
}