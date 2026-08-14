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
    
    public Carrello(Utente utente) {
        this.utente = utente;
    }
  
    public double getTotale() {
        return righe.stream()
            .mapToDouble(RigaCarrello::getSubtotale)
            .sum();
    }
    
    public int getNumeroArticoli() {
        return righe.stream()
            .mapToInt(RigaCarrello::getQuantita)
            .sum();
    }
    
    public void aggiungiProdotto(Prodotto prodotto, Integer quantita) {
        aggiungiProdotto(prodotto, quantita, null, null);
    }
    
    public void aggiungiProdotto(Prodotto prodotto, Integer quantita, String taglia, String colore) {
        if (quantita == null || quantita <= 0) return;
        
        for (RigaCarrello riga : righe) {
            if (riga.getProdotto().getId().equals(prodotto.getId())) {
                
                riga.setQuantita(riga.getQuantita() + quantita);
                if (taglia != null && !taglia.isBlank()) riga.setTaglia(taglia);
                if (colore != null && !colore.isBlank()) riga.setColore(colore);
                return;
            }
        }

        righe.add(new RigaCarrello(prodotto, quantita, taglia, colore));
    }
    
    public void rimuoviProdotto(Prodotto prodotto) {
        righe.removeIf(riga -> riga.getProdotto().getId().equals(prodotto.getId()));
    }
    
    public void svuota() {
        righe.clear();
    }
    
    public boolean isEmpty() {
        return righe.isEmpty();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public List<RigaCarrello> getRighe() {
		return righe;
	}

	public void setRighe(List<RigaCarrello> righe) {
		this.righe = righe;
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Carrello other = (Carrello) obj;
        
        if (id != null && other.id != null) {
            return id.equals(other.id);
        }
        
        return utente != null && utente.equals(other.utente);
    }
    
    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return utente != null ? utente.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Carrello{" +
               "id=" + id +
               ", utente=" + (utente != null ? utente.getEmail() : "null") +
               ", articoli=" + getNumeroArticoli() +
               ", totale=" + getTotale() +
               '}';
    }
    
}