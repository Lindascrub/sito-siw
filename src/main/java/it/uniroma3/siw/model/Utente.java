package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users") 
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
    @Email
    private String email;
    
    private String telefono;
    private String indirizzo;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "credenziali_id")
    private Credenziali credenziali;
    
    // Costruttori, getter e setter
    public Utente() {	
    }
    
    public Utente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
    
    @OneToMany(mappedBy = "utente")
    private List<Ordine> ordini = new ArrayList<>();

	public Long getId() {
		return id;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Utente other = (Utente) obj;
        
        if (id != null && other.id != null) {
            return id.equals(other.id);
        }
        
        return email != null && email.equals(other.email);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : (email != null ? email.hashCode() : 0);
    }
    
    @Override
    public String toString() {
        return "Utente{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", cognome='" + cognome + '\'' +
               ", email='" + email + '\'' +
               '}';
    }

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
   
}
