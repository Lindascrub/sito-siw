package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Taglia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "Il codice taglia è obbligatorio")
    private String codice;  // "XS", "S", "M", "L", "XL"
    
    private String descrizione;  // "Extra Small", "Small", ecc.
    
    public Taglia() {}
    
    public Taglia(String codice, String descrizione) {
        this.codice = codice;
        this.descrizione = descrizione;
    }
    
    // GETTER e SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }
    
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
}