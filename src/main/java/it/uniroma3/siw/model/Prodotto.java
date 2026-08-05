package it.uniroma3.siw.model;

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
    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;
    
    // RELAZIONE: un prodotto può avere più taglie
    @ElementCollection
    @CollectionTable(name = "prodotto_taglie")
    @Column(name = "taglie")
    private List<Taglia> taglie;
    
    @ElementCollection
    @CollectionTable(name = "prodotto_colori")
    @Column(name = "colori")
    private List<String> colori; 
    
    @Column(unique = true)
    private String codiceModello;
    
    private Boolean èattivo = true; 
    
    // Costruttore vuoto
    public Prodotto() {}

    
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

	public List<Taglia> getTaglie() {
		return taglie;
	}

	public void setTaglie(List<Taglia> taglie) {
		this.taglie = taglie;
	}

	public List<String> getColori() {
		return colori;
	}

	public void setColori(List<String> colori) {
		this.colori = colori;
	}

	public String getCodiceModello() {
		return codiceModello;
	}

	public void setCodiceModello(String codiceModello) {
		this.codiceModello = codiceModello;
	}

	public Boolean getÈattivo() {
		return èattivo;
	}

	public void setÈattivo(Boolean èattivo) {
		this.èattivo = èattivo;
	}

	//hashcode and equal 
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prodotto other = (Prodotto) obj;
		return Objects.equals(id, other.id);
	}
    
    
}