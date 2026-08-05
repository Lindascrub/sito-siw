package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;
  
    
    // Costruttore vuoto (obbligatorio per JPA)
    public Categoria() {}
    
    // Costruttore con parametri
    public Categoria(String nome, String descrizione) {
        this.nome = nome;
    }
    
    // GETTER e SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    // equals e hashCode (utili per confrontare oggetti)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return id != null && id.equals(categoria.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}