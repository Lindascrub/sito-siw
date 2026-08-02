package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Recensione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String titolo;
    
    @NotBlank
    @Column(length = 1000)
    private String testo;
    
    @Min(1) @Max(5)
    private int valutazione;
    
    private LocalDateTime dataCreazione;
    
    @ManyToOne
    private Prodotto prodotto;
    
    @ManyToOne
    private Utente utente;
    
    public Recensione() {
        this.dataCreazione = LocalDateTime.now();
    }
    
    // GETTER e SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    
    public int getValutazione() { return valutazione; }
    public void setValutazione(int valutazione) { this.valutazione = valutazione; }
    
    public LocalDateTime getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(LocalDateTime dataCreazione) { this.dataCreazione = dataCreazione; }
    
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
}