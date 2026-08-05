package it.uniroma3.siw.dto;

import java.util.Objects;

public class ArticoloCarrelloDto {
    private Long prodottoId;
    private Integer quantita;
    private String taglia;
    private String colore;
    
    public ArticoloCarrelloDto() {}
    
    public ArticoloCarrelloDto(Long prodottoId, Integer quantita) {
        this.prodottoId = prodottoId;
        this.quantita = quantita;
    }
    
    public ArticoloCarrelloDto(Long prodottoId, Integer quantita, String taglia, String colore) {
        this.prodottoId = prodottoId;
        this.quantita = quantita;
        this.taglia = taglia;
        this.colore = colore;
    }
    
    // GETTER e SETTER
    public Long getProdottoId() { return prodottoId; }
    public void setProdottoId(Long prodottoId) { this.prodottoId = prodottoId; }
    
    public Integer getQuantita() { return quantita; }
    public void setQuantita(Integer quantita) { this.quantita = quantita; }
    
    public String getTaglia() { return taglia; }
    public void setTaglia(String taglia) { this.taglia = taglia; }
    
    public String getColore() { return colore; }
    public void setColore(String colore) { this.colore = colore; }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ArticoloCarrelloDto other = (ArticoloCarrelloDto) obj;
        return Objects.equals(prodottoId, other.prodottoId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(prodottoId);
    }
    
    @Override
    public String toString() {
        return "ArticoloCarrelloDTO{" +
               "prodottoId=" + prodottoId +
               ", quantita=" + quantita +
               ", taglia='" + taglia + '\'' +
               ", colore='" + colore + '\'' +
               '}';
    }
}