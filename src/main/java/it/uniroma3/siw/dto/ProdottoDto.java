package it.uniroma3.siw.dto;

import it.uniroma3.siw.model.Prodotto;
import java.util.List;

/**
 * DTO per esporre i prodotti via API REST.
 * NON è una Entity JPA.
 */
public class ProdottoDto {
    
    private Long id;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private Integer quantitaDisponibile;
    private String urlImage;
    private String categoriaNome;
    private Long categoriaId;
    private List<String> taglieDisponibili;
    private List<String> coloriDisponibili;
    private Boolean attivo;
    

    public ProdottoDto() {}
    
    public ProdottoDto(Prodotto prodotto) {
        this.id = prodotto.getId();
        this.nome = prodotto.getNome();
        this.descrizione = prodotto.getDescrizione();
        this.prezzo = prodotto.getPrezzo();
        this.quantitaDisponibile = prodotto.getQuantitaDisponibile();
        this.urlImage = prodotto.getUrlImage();
        this.attivo = prodotto.getAttivo();
        this.taglieDisponibili = prodotto.getTaglieDisponibili();
        this.coloriDisponibili = prodotto.getColoriDisponibili();
        
        if (prodotto.getCategoria() != null) {
            this.categoriaNome = prodotto.getCategoria().getNome();
            this.categoriaId = prodotto.getCategoria().getId();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    
    public Double getPrezzo() { return prezzo; }
    public void setPrezzo(Double prezzo) { this.prezzo = prezzo; }
    
    public Integer getQuantitaDisponibile() { return quantitaDisponibile; }
    public void setQuantitaDisponibile(Integer quantitaDisponibile) { this.quantitaDisponibile = quantitaDisponibile; }
    
    public String getUrlImage() { return urlImage; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
    
    public String getCategoriaNome() { return categoriaNome; }
    public void setCategoriaNome(String categoriaNome) { this.categoriaNome = categoriaNome; }
    
    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    
    public List<String> getTaglieDisponibili() { return taglieDisponibili; }
    public void setTaglieDisponibili(List<String> taglieDisponibili) { this.taglieDisponibili = taglieDisponibili; }
    
    public List<String> getColoriDisponibili() { return coloriDisponibili; }
    public void setColoriDisponibili(List<String> coloriDisponibili) { this.coloriDisponibili = coloriDisponibili; }
    
    public Boolean getAttivo() { return attivo; }
    public void setAttivo(Boolean attivo) { this.attivo = attivo; }
}