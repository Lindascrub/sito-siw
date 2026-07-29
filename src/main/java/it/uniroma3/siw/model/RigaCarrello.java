package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RigaCarrello {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Integer quantita;
    
    @ManyToOne
    private Prodotto prodotto;
    
    public RigaCarrello() {}
    
    public RigaCarrello(Prodotto prodotto, Integer quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getQuantita() { return quantita; }
    public void setQuantita(Integer quantita) { this.quantita = quantita; }
    
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
}