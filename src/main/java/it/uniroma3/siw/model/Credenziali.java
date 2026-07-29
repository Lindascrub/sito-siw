package it.uniroma3.siw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Credenziali {
    
    public static final String DEFAULT_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    private String ruolo = DEFAULT_ROLE;
    
    @OneToOne(mappedBy = "credenziali")
    private Utente utente;
    
    // Costruttori, getter e setter
    public Credenziali() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
}