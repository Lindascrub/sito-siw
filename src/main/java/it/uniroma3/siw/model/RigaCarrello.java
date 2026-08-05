package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RigaCarrello {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Integer quantita;
    
    @ManyToOne
    private Prodotto prodotto;
    
    // =============================================
    // 🔹 AGGIUNGI QUESTI CAMPI!
    // =============================================
    private String taglia;
    private String colore;
    
    @ManyToOne
    @JoinColumn(name = "carrello_id", insertable = false, updatable = false)
    private Carrello carrello;
    
   

	// =============================================
    // COSTRUTTORI
    // =============================================
    public RigaCarrello() {
    }
    
    public RigaCarrello(Prodotto prodotto, Integer quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }
    
    public RigaCarrello(Prodotto prodotto, Integer quantita, String taglia, String colore) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.taglia = taglia;
        this.colore = colore;
    }
    
    // =============================================
    // METODI DI BUSINESS
    // =============================================
    public double getSubtotale() {
        if (prodotto == null || quantita == null) {
            return 0.0;
        }
        return prodotto.getPrezzo() * quantita;
    }
    
    // =============================================
    // GETTER E SETTER
    // =============================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getQuantita() { return quantita; }
    public void setQuantita(Integer quantita) { this.quantita = quantita; }
    
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    
    public Carrello getCarrello() {
		return carrello;
	}

	public void setCarrello(Carrello carrello) {
		this.carrello = carrello;
	}
    // =============================================
    // 🔹 AGGIUNGI QUESTI GETTER E SETTER!
    // =============================================
    public String getTaglia() { return taglia; }
    public void setTaglia(String taglia) { this.taglia = taglia; }
    
    public String getColore() { return colore; }
    public void setColore(String colore) { this.colore = colore; }
    
    // =============================================
    // EQUALS E HASHCODE
    // =============================================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        RigaCarrello other = (RigaCarrello) obj;
        
        if (id != null && other.id != null) {
            return id.equals(other.id);
        }
        
        return prodotto != null && prodotto.equals(other.prodotto);
    }
    
    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return prodotto != null ? prodotto.hashCode() : 0;
    }
}