package it.uniroma3.siw.model;

import jakarta.persistence.*;

@Entity
public class RigaOrdine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Integer quantita;
    
    private Double prezzoUnitario;
    
    @ManyToOne
    private Prodotto prodotto;
    
    @ManyToOne
    private Ordine ordine;
    
    public RigaOrdine() {}
    
    public RigaOrdine(Prodotto prodotto, Integer quantita, Double prezzoUnitario) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }
    
    // GETTER e SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getQuantita() { return quantita; }
    public void setQuantita(Integer quantita) { this.quantita = quantita; }
    
    public Double getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(Double prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }
    
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    
    public Ordine getOrdine() { return ordine; }
    public void setOrdine(Ordine ordine) { this.ordine = ordine; }
}