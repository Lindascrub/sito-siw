package it.uniroma3.siw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")  // "user" è parola riservata in PostgreSQL
public class Utente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String nome;
    
    @NotBlank
    private String cognome;
    
    @NotBlank
    @Column(unique = true)
    private String email;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Credenziali credenziali;
    
    // Costruttori, getter e setter
    public Utente() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Credenziali getCredenziali() { return credenziali; }
    public void setCredenziali(Credenziali credenziali) { this.credenziali = credenziali; }
}
