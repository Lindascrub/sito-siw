package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Prodotto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 3, max = 100, message = "Il nome non può essere troppo lungo")
    private String nome;
    
    @NotBlank(message = "La descrizione è obbligatoria")
    @Column(length = 2000)
	private String descrizione;

    @Column(nullable = false)
    @NotNull(message = "Il prezzo è obbligatorio")
    @Min(value = 0, message = "Il prezzo deve essere maggiore di 0")
    private Double prezzo;
    
    @Column(nullable = false)
    @NotNull(message = "La quantità è obbligatoria")
    @Min(value = 0, message = "La quantità non può essere negativa")
    private Integer quantitaDisponibile;
    
    private String urlImage;
    
    // RELAZIONE: un prodotto appartiene a una categoria
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false) 
    private Categoria categoria;
    
    // RELAZIONE: un prodotto può avere più taglie
    @ElementCollection
    @CollectionTable(name = "prodotto_taglie")
    @Column(name = "taglia")
    private List<String> taglieDisponibili = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "prodotto_colori")
    @Column(name = "colori")
    private List<String> coloriDisponibili = new ArrayList<>(); 
    
    @Column(unique = true)
    private String codiceModello;
    
    private Boolean attivo = true; 
    
    // Costruttore vuoto
    public Prodotto() {
    	
    }
    
    public Prodotto(String nome, Double prezzo) {
        this.nome = nome;
        this.prezzo = prezzo;
    }
    
    public Prodotto(String nome, String descrizione, Double prezzo, Categoria categoria) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.categoria = categoria;
    }
    
    // getter e setter
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public Integer getQuantitaDisponibile() {
		return quantitaDisponibile;
	}

	public void setQuantitaDisponibile(Integer quantitaDisponibile) {
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<String> getTaglieDisponibili() {
		return taglieDisponibili;
	}

	public void setTaglieDisponibili(List<String> taglieDisponibili) {
		this.taglieDisponibili = taglieDisponibili;
	}

	public List<String> getColoriDisponibili() {
		return coloriDisponibili;
	}

	public void setColoriDisponibili(List<String> coloriDisponibili) {
		this.coloriDisponibili = coloriDisponibili;
	}

	public String getCodiceModello() {
		return codiceModello;
	}

	public void setCodiceModello(String codiceModello) {
		this.codiceModello = codiceModello;
	}

	
	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	//hashcode and equal 
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Prodotto other = (Prodotto) obj;
        
        // Se entrambi hanno id, confronto per id
        if (id != null && other.id != null) {
            return id.equals(other.id);
        }
        
        // Se id è null, confronto per nome + categoria
        if (id == null && other.id == null) {
            return Objects.equals(nome, other.nome) && 
                   Objects.equals(categoria, other.categoria);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return Objects.hash(nome, categoria);
    }
    @Override
    public String toString() {
        return "Prodotto{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", prezzo=" + prezzo +
               ", categoria=" + (categoria != null ? categoria.getNome() : "null") +
               '}';
    }
}