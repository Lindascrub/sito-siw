package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordini")
public class Ordine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private LocalDateTime dataCreazione;
    
    @Enumerated(EnumType.STRING)
    private StatoOrdine stato;
    
    private Double totale;
    
    @ManyToOne
    private Utente utente;
    
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RigaOrdine> righe = new ArrayList<>();
    
    public Ordine() {
        this.dataCreazione = LocalDateTime.now();
        this.stato = StatoOrdine.CREATO;
    }
    
    // GETTER e SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDateTime getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(LocalDateTime dataCreazione) { this.dataCreazione = dataCreazione; }
    
    public StatoOrdine getStato() { return stato; }
    public void setStato(StatoOrdine stato) { this.stato = stato; }
    
    public Double getTotale() { return totale; }
    public void setTotale(Double totale) { this.totale = totale; }
    
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
    
    public List<RigaOrdine> getRighe() { return righe; }
    public void setRighe(List<RigaOrdine> righe) { this.righe = righe; }
}