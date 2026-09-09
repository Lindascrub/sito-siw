package it.uniroma3.siw.dto;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.RigaCarrello;

import java.util.List;

/** DTO per esporre il carrello via API REST. NON è una Entity JPA. */
public class CarrelloDto {

    public static class RigaDto {
        private Long prodottoId;
        private String nome;
        private String urlImage;
        private Double prezzo;
        private Integer quantita;
        private String taglia;
        private String colore;
        private Double subtotale;

        public RigaDto(RigaCarrello riga) {
            this.prodottoId = riga.getProdotto().getId();
            this.nome = riga.getProdotto().getNome();
            this.urlImage = riga.getProdotto().getUrlImage();
            this.prezzo = riga.getProdotto().getPrezzo();
            this.quantita = riga.getQuantita();
            this.taglia = riga.getTaglia();
            this.colore = riga.getColore();
            this.subtotale = riga.getSubtotale();
        }

        public Long getProdottoId() { return prodottoId; }
        public String getNome() { return nome; }
        public String getUrlImage() { return urlImage; }
        public Double getPrezzo() { return prezzo; }
        public Integer getQuantita() { return quantita; }
        public String getTaglia() { return taglia; }
        public String getColore() { return colore; }
        public Double getSubtotale() { return subtotale; }
    }

    private List<RigaDto> righe;
    private double totale;
    private int numeroArticoli;

    public CarrelloDto(Carrello carrello) {
        this.righe = carrello.getRighe().stream().map(RigaDto::new).toList();
        this.totale = carrello.getTotale();
        this.numeroArticoli = carrello.getNumeroArticoli();
    }

    public List<RigaDto> getRighe() { return righe; }
    public double getTotale() { return totale; }
    public int getNumeroArticoli() { return numeroArticoli; }
}
