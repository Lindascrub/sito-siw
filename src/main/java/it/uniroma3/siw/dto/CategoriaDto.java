package it.uniroma3.siw.dto;

import it.uniroma3.siw.model.Categoria;

/** DTO per esporre le categorie via API REST. NON è una Entity JPA. */
public class CategoriaDto {

    private Long id;
    private String nome;
    private String descrizione;
    private int numeroProdotti;

    public CategoriaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descrizione = categoria.getDescrizione();
        this.numeroProdotti = categoria.getProdotti() != null ? categoria.getProdotti().size() : 0;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public int getNumeroProdotti() { return numeroProdotti; }
}
