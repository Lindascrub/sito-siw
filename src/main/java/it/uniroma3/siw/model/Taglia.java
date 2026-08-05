package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Taglia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "Il nome della taglia è obbligatorio")
    private String nome;  // "XS", "S", "M", "L", "XL"
    
    @NotNull(message = "L'ordine è obbligatorio")
    @Column(nullable = false)
    private Integer ordine; 
    
    private String descrizione;  // "Extra Small", "Small", ecc.
    
    public Taglia() {}
    
    public Taglia(String nome, Integer ordine) {
        this.nome = nome;
        this.ordine = ordine;
    }

    // GETTER e SETTER
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        
	        Taglia other = (Taglia) obj;
	        if (id != null && other.id != null) {
	            return id.equals(other.id);
	        }
	        return nome != null && nome.equals(other.nome);
	    }
	    
	    @Override
	    public int hashCode() {
	        if (id != null) {
	            return id.hashCode();
	        }
	        return nome != null ? nome.hashCode() : 0;
	    }
	    

	    @Override
	    public String toString() {
	        return "Taglia{" +
	               "id=" + id +
	               ", nome='" + nome + '\'' +
	               ", ordine=" + ordine +
	               '}';
	    }

    
}