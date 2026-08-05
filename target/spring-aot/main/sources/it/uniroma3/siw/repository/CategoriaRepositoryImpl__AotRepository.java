package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link CategoriaRepository}.
 */
@Generated
public class CategoriaRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public CategoriaRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link CategoriaRepository#findByNome(java.lang.String)}.
   */
  public List<Categoria> findByNome(String nome) {
    String queryString = "SELECT c FROM Categoria c WHERE c.nome = :nome";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("nome", nome);

    return (List<Categoria>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link CategoriaRepository#findByNomeContainingIgnoreCase(java.lang.String)}.
   */
  public List<Categoria> findByNomeContainingIgnoreCase(String nome) {
    String queryString = "SELECT c FROM Categoria c WHERE UPPER(c.nome) LIKE UPPER(:nome) ESCAPE '\\'";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("nome", "%%%s%%".formatted(nome != null ? nome.toUpperCase() : nome));

    return (List<Categoria>) query.getResultList();
  }
}
