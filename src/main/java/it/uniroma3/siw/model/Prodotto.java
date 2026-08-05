package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Prodotto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;
    
    private String descrizione;
    
    @NotNull(message = "Il prezzo è obbligatorio")
    @Min(value = 0, message = "Il prezzo deve essere maggiore di 0")
    private Double prezzo;
    
    @NotNull(message = "La quantità è obbligatoria")
    @Min(value = 0, message = "La quantità non può essere negativa")
    private Integer quantitaDisponibile;
    
    private String urlImage;
    
    // RELAZIONE: un prodotto appartiene a una categoria
    @ManyToOne
    private Categoria categoria;
    
    // RELAZIONE: un prodotto può avere più taglie
    @ManyToMany
    private List<Taglia> taglie;
    
    // Costruttore vuoto
    public Prodotto() {}
    
    // GETTER e SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    
    public Double getPrezzo() { return prezzo; }
    public void setPrezzo(Double prezzo) { this.prezzo = prezzo; }
    
    public Integer getQuantitaDisponibile() { return quantitaDisponibile; }
    public void setQuantitaDisponibile(Integer quantitaDisponibile) { 
        this.quantitaDisponibile = quantitaDisponibile; 
    }
    
    public String getUrlImmagine() { return urlImage; }
    public void setUrlImmagine(String urlImage) { this.urlImage = urlImage; }
    
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    
    public List<Taglia> getTaglie() { return taglie; }
    public void setTaglie(List<Taglia> taglie) { this.taglie = taglie; }
}