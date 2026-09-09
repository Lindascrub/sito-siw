package it.uniroma3.siw.dto;

import it.uniroma3.siw.model.Recensione;

import java.time.LocalDateTime;

/**
 * DTO per esporre le recensioni via API REST.
 * Non espone mai l'entity Utente (contiene le credenziali collegate).
 */
public class RecensioneDto {

    private Long id;
    private String titolo;
    private String testo;
    private int valutazione;
    private LocalDateTime dataCreazione;
    private String autoreNome;

    public RecensioneDto() {}

    public RecensioneDto(Recensione recensione) {
        this.id = recensione.getId();
        this.titolo = recensione.getTitolo();
        this.testo = recensione.getTesto();
        this.valutazione = recensione.getValutazione();
        this.dataCreazione = recensione.getDataCreazione();
        if (recensione.getUtente() != null) {
            this.autoreNome = recensione.getUtente().getNome() + " " + recensione.getUtente().getCognome();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }

    public int getValutazione() { return valutazione; }
    public void setValutazione(int valutazione) { this.valutazione = valutazione; }

    public LocalDateTime getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(LocalDateTime dataCreazione) { this.dataCreazione = dataCreazione; }

    public String getAutoreNome() { return autoreNome; }
    public void setAutoreNome(String autoreNome) { this.autoreNome = autoreNome; }
}
