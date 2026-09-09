package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "righe_ordine")
public class RigaOrdine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordine_id")
    private Ordine ordine;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Prodotto prodotto;
    
    private Integer quantita;
    
    private Double prezzoUnitario;
    
    private String taglia;
    private String colore;

    /** Nome del prodotto al momento dell'ordine: resta leggibile anche se il prodotto viene poi eliminato. */
    private String nomeProdotto;

    public RigaOrdine() {
    }
    
    public RigaOrdine(Prodotto prodotto, Integer quantita, Double prezzo) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzo;
    }
    
    public RigaOrdine(Ordine ordine, Prodotto prodotto, Integer quantita, Double prezzo) {
        this.ordine = ordine;
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzo;
    }
    
    public Double getSubtotale() {
        return prezzoUnitario * quantita;
    }

   
    // GETTER e SETTER

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}



	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public Double getPrezzoUnitario() {
		return prezzoUnitario;
	}

	public void setPrezzoUnitario(Double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getNomeProdotto() {
		return nomeProdotto;
	}

	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}

	/** Nome da mostrare: quello salvato al momento dell'ordine, con un fallback se mancante. */
	public String getNomeProdottoVisualizzato() {
		if (nomeProdotto != null) return nomeProdotto;
		if (prodotto != null) return prodotto.getNome();
		return "Prodotto rimosso";
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        RigaOrdine other = (RigaOrdine) obj;
        
        if (id != null && other.id != null) {
            return id.equals(other.id);
        }
        
        return ordine != null && 
               ordine.equals(other.ordine) &&
               prodotto != null && 
               prodotto.equals(other.prodotto);
    }
    
    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return Objects.hash(ordine, prodotto);
    }
    
    @Override
    public String toString() {
        return "RigaOrdine{" +
               "id=" + id +
               ", prodotto=" + (prodotto != null ? prodotto.getNome() : "null") +
               ", quantita=" + quantita +
               ", prezzo=" + prezzoUnitario +
               ", subtotale=" + getSubtotale() +
               '}';
    }
}    