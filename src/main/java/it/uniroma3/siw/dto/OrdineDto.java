package it.uniroma3.siw.dto;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.RigaOrdine;

import java.time.LocalDateTime;
import java.util.List;

/** DTO per esporre un ordine via API REST. NON è una Entity JPA. */
public class OrdineDto {

    public static class RigaDto {
        private String prodottoNome;
        private Integer quantita;
        private String taglia;
        private String colore;
        private Double subtotale;

        public RigaDto(RigaOrdine riga) {
            this.prodottoNome = riga.getNomeProdottoVisualizzato();
            this.quantita = riga.getQuantita();
            this.taglia = riga.getTaglia();
            this.colore = riga.getColore();
            this.subtotale = riga.getSubtotale();
        }

        public String getProdottoNome() { return prodottoNome; }
        public Integer getQuantita() { return quantita; }
        public String getTaglia() { return taglia; }
        public String getColore() { return colore; }
        public Double getSubtotale() { return subtotale; }
    }

    private Long id;
    private String stato;
    private Double totale;
    private LocalDateTime dataOrdine;
    private LocalDateTime dataPagamento;
    private String metodoPagamento;
    private String indirizzoSpedizione;
    private String cittaSpedizione;
    private String codPostaleSpedizione;
    private String utenteEmail;
    private List<RigaDto> righe;

    public OrdineDto(Ordine ordine) {
        this.id = ordine.getId();
        this.stato = ordine.getStato().name();
        this.totale = ordine.getTotale();
        this.dataOrdine = ordine.getDataOrdine();
        this.dataPagamento = ordine.getDataPagamento();
        this.metodoPagamento = ordine.getMetodoPagamento();
        this.indirizzoSpedizione = ordine.getIndirizzoSpedizione();
        this.cittaSpedizione = ordine.getCittaSpedizione();
        this.codPostaleSpedizione = ordine.getCodPostaleSpedizione();
        this.utenteEmail = ordine.getUtente() != null ? ordine.getUtente().getEmail() : null;
        this.righe = ordine.getRighe().stream().map(RigaDto::new).toList();
    }

    public Long getId() { return id; }
    public String getStato() { return stato; }
    public Double getTotale() { return totale; }
    public LocalDateTime getDataOrdine() { return dataOrdine; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public String getMetodoPagamento() { return metodoPagamento; }
    public String getIndirizzoSpedizione() { return indirizzoSpedizione; }
    public String getCittaSpedizione() { return cittaSpedizione; }
    public String getCodPostaleSpedizione() { return codPostaleSpedizione; }
    public String getUtenteEmail() { return utenteEmail; }
    public List<RigaDto> getRighe() { return righe; }
}
