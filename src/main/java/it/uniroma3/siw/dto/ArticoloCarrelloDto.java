package it.uniroma3.siw.dto;

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
}