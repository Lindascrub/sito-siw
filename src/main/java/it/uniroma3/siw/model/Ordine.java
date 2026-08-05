package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ordini")
public class Ordine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime dataOrdine;
    
    @Enumerated(EnumType.STRING)
    private StatoOrdine stato;
    
    private Double totale;
    
    @NotBlank
    private String indirizzoSpedizione;
    
    @NotBlank
    private String cittaSpedizione;
    
    @NotBlank
    private String codPostaleSpedizione;
    
    @ManyToOne
    private Utente utente;
    
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RigaOrdine> righe = new ArrayList<>();
    
    private LocalDateTime dataPagamento;
    private String metodoPagamento;
    
    public Ordine() {
    }
    
    public Ordine(Utente utente) {
        this.utente = utente;
        this.dataOrdine = LocalDateTime.now();
        this.stato = StatoOrdine.CREATO;
    }
    
    public void calcolaTotale() {
        this.totale = righe.stream()
            .mapToDouble(RigaOrdine::getSubtotale)
            .sum();
    }
    
    // GETTER e SETTER
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDataOrdine() {
		return dataOrdine;
	}
	public void setDataOrdine(LocalDateTime dataOrdine) {
		this.dataOrdine = dataOrdine;
	}
	public StatoOrdine getStato() {
		return stato;
	}
	public void setStato(StatoOrdine stato) {
		this.stato = stato;
	}
	public Double getTotale() {
		return totale;
	}
	public void setTotale(Double totale) {
		this.totale = totale;
	}
	public String getIndirizzoSpedizione() {
		return indirizzoSpedizione;
	}
	public void setIndirizzoSpedizione(String indirizzoSpedizione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public String getCittaSpedizione() {
		return cittaSpedizione;
	}

	public void setCittaSpedizione(String cittaSpedizione) {
		this.cittaSpedizione = cittaSpedizione;
	}

	public String getCodPostaleSpedizione() {
		return codPostaleSpedizione;
	}

	public void setCodPostaleSpedizione(String codPostaleSpedizione) {
		this.codPostaleSpedizione = codPostaleSpedizione;
	}

	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public List<RigaOrdine> getRighe() {
		return righe;
	}
	public void setRighe(List<RigaOrdine> righe) {
		this.righe = righe;
	}


	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    
	    Ordine other = (Ordine) obj;
	    
	    if (id != null && other.id != null) {
	        return id.equals(other.id);
	    }
	    
	    // Fallback: dataOrdine + utente (identificatori business)
	    return dataOrdine != null && 
	           dataOrdine.equals(other.dataOrdine) &&
	           utente != null && 
	           utente.equals(other.utente);
	}

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    }
	    return Objects.hash(dataOrdine, utente);
	}
}
